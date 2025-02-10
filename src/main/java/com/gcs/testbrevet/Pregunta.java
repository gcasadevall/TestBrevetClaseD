package com.gcs.testbrevet;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Pregunta {
    private final String pregunta;
    private final List<Opcion> opciones;
    
    public Pregunta(String pregunta) {
        super();
        this.pregunta = pregunta;
        this.opciones = new LinkedList<>();
    }
    
    public void addOpcion(String opcion, boolean correcta) {
        opciones.add(new Opcion(opcion, correcta));
    }

    public String getPregunta() {
        return "¿ "+pregunta+" ?";
    }

    public List<Opcion> getOpciones() {
        Collections.shuffle(opciones); 
        return opciones;
    }
    
    void addOpcion(String opcion) {
        opciones.add(new Opcion(opcion));
    }
    
    
}
