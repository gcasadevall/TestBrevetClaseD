package com.gcs.testbrevet;

/**
 * Programa para prepararse para el brevet categoria D
 * Usa las preguntas del anexo Bravo para categoria D
 * @author Gonzalo Casadevall
 */
public class TestBrevet {
   
    public static void main(String[] args) {
        
        PruebaBrevet p = new PruebaBrevet();
        
        if (args != null && args.length != 0) {
            
            if (args[0].equalsIgnoreCase("listar")) {
                p.listar();
            } else if (args[0].equalsIgnoreCase("realizar")) {
                p.realizar();
            } else if (args[0].equalsIgnoreCase("realizarConRespuesta")) {
                p.realizar(true);
            }
            
        } else {
            System.out.println("TestBrevet\nUso: TestBrevet <OPCION>\nOpciones posibles:\n\tlistar - Lista las preguntas con sus respuestas correctas\n\trealizar - Realiza las preguntas con opcion multiple.\n\trealizarConRespuesta - Realiza el test y si te equivocas te dice cual era la respuesta correcta.");
        }
        
    }
}
