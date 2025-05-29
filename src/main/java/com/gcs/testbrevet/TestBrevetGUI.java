package com.gcs.testbrevet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.ButtonModel; // For optionsGroup.getSelection()
import javax.swing.JOptionPane; // For selectable test modes
import java.awt.Dimension; // For setMinimumSize
// import java.util.Collections; // Not needed here, but in PruebaBrevet

public class TestBrevetGUI extends JFrame {

    private static final int MINIMUM_PASS_SCORE = 70; // Passing threshold

    private PruebaBrevet pruebaBrevet;
    // Declare GUI components
    private JTextArea questionTextArea;
    private JPanel optionsPanel;
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private JLabel feedbackLabel;
    private JLabel questionStatusLabel;
    private JLabel scoreStatusLabel;

    // Variables for test logic
    private List<Pregunta> preguntasDelTest;
    private int currentQuestionIndex;
    private Pregunta preguntaActual;
    private int totalPreguntas;
    private int correctAnswers;
    private int incorrectAnswers;
    private boolean showCorrectAnswerOnMistake;

    public TestBrevetGUI(boolean showCorrectAnswerOnMistake) {
        this.pruebaBrevet = new PruebaBrevet();
        this.preguntasDelTest = new ArrayList<>(pruebaBrevet.getPreguntas()); // Get questions
        this.totalPreguntas = this.preguntasDelTest.size();
        this.currentQuestionIndex = 0;
        this.correctAnswers = 0;
        this.incorrectAnswers = 0;
        this.showCorrectAnswerOnMistake = showCorrectAnswerOnMistake;

        setTitle("Test Brevet - Categoría D");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Changed for custom handling
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 500)); // Set minimum frame size
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Custom close operation

        setLayout(new BorderLayout(10, 10)); // Add some spacing

        // Initialize components
        questionTextArea = new JTextArea("Aquí irá la pregunta...");
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setLineWrap(true);
        questionTextArea.setEditable(false);
        questionTextArea.setFont(new Font("SansSerif", Font.BOLD, 16));
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        questionScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS)); // Or GridLayout
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add some padding
        optionsGroup = new ButtonGroup();
        // Radio buttons will be added dynamically later

        submitButton = new JButton("Siguiente Pregunta");
        submitButton.addActionListener(new SubmitButtonListener()); // Add ActionListener
        feedbackLabel = new JLabel(" "); // Placeholder for feedback
        feedbackLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        
        questionStatusLabel = new JLabel("Pregunta: -/-");
        questionStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        scoreStatusLabel = new JLabel("Correctas: 0 | Incorrectas: 0"); // Initial score display
        scoreStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Layout components
        add(questionScrollPane, BorderLayout.NORTH);
        add(new JScrollPane(optionsPanel), BorderLayout.CENTER); // Options panel in a scroll pane too

        JPanel southPanel = new JPanel(new BorderLayout(10,5)); // Main south panel
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel statusPanel = new JPanel(new GridLayout(1,2,10,5)); // For status labels
        statusPanel.add(questionStatusLabel);
        statusPanel.add(scoreStatusLabel);
        
        southPanel.add(statusPanel, BorderLayout.NORTH);
        southPanel.add(submitButton, BorderLayout.CENTER);
        southPanel.add(feedbackLabel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);

        // Load first question
        displayQuestion(currentQuestionIndex);

        // Add window listener for custom close operation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Check if the test is currently active.
                // A simple check is if the submit button is enabled AND the test isn't at the very end.
                if (submitButton.isEnabled() && currentQuestionIndex < totalPreguntas) {
                    int choice = JOptionPane.showConfirmDialog(TestBrevetGUI.this,
                            "¿Está seguro de que desea salir? El progreso actual del test se perderá.",
                            "Confirmar Salida",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (choice == JOptionPane.YES_OPTION) {
                        dispose(); // Closes the window
                        System.exit(0); // Terminates the application
                    }
                    // If NO_OPTION or dialog is closed, do nothing.
                } else {
                    // Test is finished, or was never properly started in a state where it could be quit.
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    private void displayQuestion(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= totalPreguntas) {
            questionTextArea.setText("¡Test Completado!"); // Updated message
            optionsPanel.removeAll(); // Clear options
            optionsPanel.revalidate();
            optionsPanel.repaint();
            submitButton.setEnabled(false); // Disable submit button
            questionStatusLabel.setText("Resultados Finales"); // Update status label

            // Construct detailed result message for feedbackLabel
            String resultMessage = String.format(
                "<html>Total de Preguntas: %d<br>" +
                "Respuestas Correctas: %d<br>" +
                "Respuestas Incorrectas: %d<br><br>",
                totalPreguntas, correctAnswers, incorrectAnswers
            );

            if (correctAnswers >= MINIMUM_PASS_SCORE) {
                resultMessage += "<font color='green' size='+1'><b>¡FELICITACIONES! Ha aprobado.</b></font></html>";
            } else {
                resultMessage += "<font color='red' size='+1'><b>LO SIENTO. Debe estudiar más.</b></font></html>";
            }
            feedbackLabel.setText(resultMessage);
            
            // Update scoreStatusLabel to reflect final scores accurately
            scoreStatusLabel.setText(String.format("Correctas: %d | Incorrectas: %d", correctAnswers, incorrectAnswers));
            return;
        }

        this.currentQuestionIndex = questionIndex; // Update current index
        this.preguntaActual = preguntasDelTest.get(questionIndex);
        questionTextArea.setText("<html><body style='width: 500px'>" + (questionIndex + 1) + ") " + preguntaActual.getPregunta() + "</body></html>");

        optionsPanel.removeAll();
        optionsGroup = new ButtonGroup(); 

        for (Opcion opcion : preguntaActual.getOpciones()) {
            // Using HTML to allow text wrapping for long options. Adjust width as needed.
            JRadioButton optionButton = new JRadioButton("<html><body style='width: 450px'>" + opcion.getOpcion() + "</body></html>");
            optionButton.setActionCommand(opcion.getOpcion()); 
            optionButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
            // optionButton.setOpaque(false); // If using custom panel background
            optionsGroup.add(optionButton);
            optionsPanel.add(optionButton);
            if (optionsPanel.getLayout() instanceof BoxLayout) {
                 optionsPanel.add(Box.createRigidArea(new Dimension(0, 8))); // Add some vertical spacing
            }
        }

        questionStatusLabel.setText("Pregunta: " + (questionIndex + 1) + " de " + totalPreguntas);
        feedbackLabel.setText("Seleccione una opción y presione 'Siguiente Pregunta'."); 
        submitButton.setEnabled(true); 
        // submitButton.setText("Siguiente Pregunta"); // Ensure text is correct if it changes

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    // Getter methods for testing
    public int getCorrectAnswers() { return correctAnswers; }
    public int getIncorrectAnswers() { return incorrectAnswers; }
    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
    public int getTotalPreguntas() { return totalPreguntas; }
    public Pregunta getPreguntaActual() { return preguntaActual; }

    // Main method (already exists)
    public static void main(String[] args) {
        Object[] options = {"Mostrar respuesta al errar", "Ocultar respuesta al errar"};
        int choice = JOptionPane.showOptionDialog(null, // parent component
                "Seleccione el modo de prueba:", // message
                "Modo de Prueba", // title
                JOptionPane.YES_NO_OPTION, // option type
                JOptionPane.QUESTION_MESSAGE, // message type
                null, // icon
                options, // options
                options[0]); // default option

        // YES_OPTION corresponds to the first button "Mostrar respuesta al errar"
        final boolean finalShowAnswerMode = (choice == JOptionPane.YES_OPTION);

        // Ensure GUI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TestBrevetGUI(finalShowAnswerMode).setVisible(true);
            }
        });
    }

    // Inner class for submit button action
    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonModel selectedModel = optionsGroup.getSelection();

            if (selectedModel == null) {
                feedbackLabel.setText("Por favor, seleccione una opción.");
                return;
            }

            String selectedOptionText = selectedModel.getActionCommand();
            String correctOptionText = ""; // Will hold the text of the correct option
            boolean isCorrect = false;

            // Determine if the selected option is correct and find the correct option's text
            for (Opcion opcion : preguntaActual.getOpciones()) {
                if (opcion.isCorrecta()) {
                    correctOptionText = opcion.getOpcion(); // Store the correct option text
                    if (opcion.getOpcion().equals(selectedOptionText)) {
                        isCorrect = true;
                    }
                    // No need to break here if we want to ensure correctOptionText is always set
                    // from the loop, though in current logic it's fine.
                }
            }
             // Defensive break, assuming only one correct answer. If multiple could be marked correct, this needs adjustment.
            for (Opcion opcion : preguntaActual.getOpciones()) {
                if (opcion.isCorrecta()) {
                    correctOptionText = opcion.getOpcion();
                    break; 
                }
            }


            if (isCorrect) {
                correctAnswers++;
                feedbackLabel.setText("¡Correcto!");
            } else {
                incorrectAnswers++;
                if (showCorrectAnswerOnMistake) { // Accessing outer class member
                    feedbackLabel.setText("<html><body style='width: 400px'>Incorrecto. La respuesta correcta era: <br/><b>" + correctOptionText + "</b></body></html>");
                } else {
                    feedbackLabel.setText("Incorrecto.");
                }
            }

            scoreStatusLabel.setText("Correctas: " + correctAnswers + " | Incorrectas: " + incorrectAnswers);
            
            currentQuestionIndex++;
            // displayQuestion will handle the end-of-test logic
            // (e.g., when currentQuestionIndex >= totalPreguntas)
            displayQuestion(currentQuestionIndex);
        }
    }
}
