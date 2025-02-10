package com.gcs.testbrevet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PruebaNautica {

    // Clase para representar una pregunta y sus respuestas
    static class Pregunta {
        String pregunta;
        String respuestaCorrecta;
        List<String> opciones;

        Pregunta(String pregunta, String respuestaCorrecta) {
            this.pregunta = pregunta;
            this.respuestaCorrecta = respuestaCorrecta;
            this.opciones = new ArrayList<>();
        }

        void agregarOpcion(String opcion) {
            this.opciones.add(opcion);
        }

        void mezclarOpciones() {
            Collections.shuffle(this.opciones);
        }
    }

    // Cargar preguntas desde el archivo
    static List<Pregunta> cargarPreguntas(String filePath) throws IOException {
        List<Pregunta> preguntas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        Pregunta preguntaActual = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("Preguntas que evaluar")) {
                continue; // Ignorar la línea de encabezado
            }
            if (line.matches("\\d+.*")) { // Si la línea comienza con un número (pregunta)
                if (preguntaActual != null) {
                    preguntas.add(preguntaActual);
                }
                String[] partes = line.split(" ", 2); // Separar el número de la pregunta
                preguntaActual = new Pregunta(partes[1], ""); // Crear nueva pregunta
            } else if (preguntaActual != null && !line.isEmpty()) {
                if (preguntaActual.respuestaCorrecta.isEmpty()) {
                    preguntaActual.respuestaCorrecta = line; // La primera línea después de la pregunta es la respuesta correcta
                }
                preguntaActual.agregarOpcion(line); // Agregar opción (correcta o incorrecta)
            }
        }

        if (preguntaActual != null) {
            preguntas.add(preguntaActual);
        }

        reader.close();
        return preguntas;
    }

    // Generar opciones múltiples para cada pregunta
    static void generarOpcionesMultiples(List<Pregunta> preguntas) {
        for (Pregunta pregunta : preguntas) {
            // Mezclar las opciones para que la respuesta correcta no siempre esté en la misma posición
            pregunta.mezclarOpciones();
        }
    }

    // Mostrar la pregunta y las opciones
    static int mostrarPregunta(Pregunta pregunta, Scanner scanner) {
        System.out.println("\n" + pregunta.pregunta);
        for (int i = 0; i < pregunta.opciones.size(); i++) {
            System.out.println((i + 1) + ". " + pregunta.opciones.get(i));
        }
        System.out.print("Selecciona una opción (1-" + pregunta.opciones.size() + "): ");
        return scanner.nextInt();
    }

    // Evaluar la respuesta
    static boolean evaluarRespuesta(Pregunta pregunta, int respuesta) {
        return pregunta.opciones.get(respuesta - 1).equals(pregunta.respuestaCorrecta);
    }

    // Función principal
    public static void main(String[] args) {
        String filePath = "C:\\DESA\\TestBrevetClaseD\\src\\main\\java\\com\\gcs\\testbrevet\\Preguntas.txt"; // Ruta al archivo de preguntas
        List<Pregunta> preguntas;
        try {
            preguntas = cargarPreguntas(filePath);
            generarOpcionesMultiples(preguntas);

            Scanner scanner = new Scanner(System.in);
            int correctas = 0;
            int totalPreguntas = preguntas.size();

            for (Pregunta pregunta : preguntas) {
                int respuesta = mostrarPregunta(pregunta, scanner);
                if (evaluarRespuesta(pregunta, respuesta)) {
                    correctas++;
                    System.out.println("¡Correcto!");
                } else {
                    System.out.println("Incorrecto. La respuesta correcta es: " + pregunta.respuestaCorrecta);
                }
            }

            double porcentaje = (double) correctas / totalPreguntas * 100;
            System.out.printf("\nHas respondido %d de %d preguntas correctamente (%.2f%%).\n", correctas, totalPreguntas, porcentaje);

            if (porcentaje >= 70) {
                System.out.println("¡Felicidades, has aprobado!");
            } else {
                System.out.println("Lo siento, no has alcanzado el 70% necesario para aprobar.");
            }

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de preguntas: " + e.getMessage());
        }
    }
}