package com.gcs.testbrevet;

/**
 * Programa para prepararse para el brevet categoria D
 * Usa las preguntas del anexo Bravo para categoria D
 * @author Gonzalo Casadevall
 */
public class TestBrevet {
   
    public static void main(String[] args) {
        
        if (args != null && args.length > 0 && args[0].equalsIgnoreCase("-gui")) {
            // Call the main method of TestBrevetGUI to launch it
            // This will trigger TestBrevetGUI's own mode selection dialog.
            TestBrevetGUI.main(null); 
            return; // Exit after launching GUI
        }
        
        PruebaBrevet p = new PruebaBrevet(); // Keep this for console mode
        
        if (args != null && args.length != 0) {
            // Existing console argument handling
            if (args[0].equalsIgnoreCase("listar")) {
                p.listar();
            } else if (args[0].equalsIgnoreCase("realizar")) {
                p.realizar();
            } else if (args[0].equalsIgnoreCase("realizarConRespuesta")) {
                p.realizar(true);
            } else {
                // Updated usage message if an unknown console arg is given
                System.out.println("Opción de consola no reconocida.\nTestBrevet\nUso: TestBrevet <OPCION>\nOpciones posibles:\n\t-gui - Inicia la interfaz gráfica\n\tlistar - Lista las preguntas con sus respuestas correctas\n\trealizar - Realiza las preguntas con opcion multiple.\n\trealizarConRespuesta - Realiza el test y si te equivocas te dice cual era la respuesta correcta.");
            }
        } else {
            // Updated usage message if no args are given
            System.out.println("TestBrevet\nUso: TestBrevet <OPCION>\nOpciones posibles:\n\t-gui - Inicia la interfaz gráfica\n\tlistar - Lista las preguntas con sus respuestas correctas\n\trealizar - Realiza las preguntas con opcion multiple.\n\trealizarConRespuesta - Realiza el test y si te equivocas te dice cual era la respuesta correcta.");
        }
    }
}
