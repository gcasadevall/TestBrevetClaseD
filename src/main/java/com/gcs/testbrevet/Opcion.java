package com.gcs.testbrevet;

public class Opcion {
    private String opcion;
    private boolean correcta;
    
    public Opcion(String opcion){
        this(opcion, false);
    }
    
    public Opcion(String opcion, boolean correcta) {
        super();
        this.correcta = correcta;
        this.opcion = opcion;
    }

    public String getOpcion() {
        return opcion;
    }

    public boolean isCorrecta() {
        return correcta;
    }    
    
}
