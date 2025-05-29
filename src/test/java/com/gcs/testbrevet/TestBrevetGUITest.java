package com.gcs.testbrevet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*; // Required for SwingUtilities if used directly

public class TestBrevetGUITest {

    private TestBrevetGUI testBrevetGUI;

    @BeforeEach
    void setUp() throws Exception {
        // To test Swing components, it's best to run on the Event Dispatch Thread (EDT).
        // However, for simple state tests not involving rendering or events, direct instantiation can sometimes work.
        // For robustness with Swing, even for non-rendering tests, using invokeAndWait is safer.
        // We are passing 'true' for showCorrectAnswerOnMistake mode by default for testing.
        SwingUtilities.invokeAndWait(() -> testBrevetGUI = new TestBrevetGUI(true));
    }

    @Test
    @DisplayName("GUI should initialize with default scores and question index.")
    void testInitialState() {
        assertEquals(0, testBrevetGUI.getCorrectAnswers(), "Initial correct answers should be 0.");
        assertEquals(0, testBrevetGUI.getIncorrectAnswers(), "Initial incorrect answers should be 0.");
        assertEquals(0, testBrevetGUI.getCurrentQuestionIndex(), "Initial question index should be 0.");
    }

    @Test
    @DisplayName("GUI should load questions from PruebaBrevet on initialization.")
    void testLoadsQuestions() {
        assertTrue(testBrevetGUI.getTotalPreguntas() > 0, "Total questions should be greater than 0.");
        assertNotNull(testBrevetGUI.getPreguntaActual(), "Current question (preguntaActual) should not be null after init.");
        // Assuming PruebaBrevet loads at least one question.
        // We can also check if the text area for the question is populated if we make it accessible.
    }
    
    // Getter methods needed in TestBrevetGUI for these tests:
    // public int getCorrectAnswers() { return correctAnswers; }
    // public int getIncorrectAnswers() { return incorrectAnswers; }
    // public int getCurrentQuestionIndex() { return currentQuestionIndex; }
    // public int getTotalPreguntas() { return totalPreguntas; }
    // public Pregunta getPreguntaActual() { return preguntaActual; }
}
