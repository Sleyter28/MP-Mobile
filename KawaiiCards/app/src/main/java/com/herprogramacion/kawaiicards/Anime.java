package com.herprogramacion.kawaiicards;

/**
 * Creado por Hermosa Programación
 */
public class Anime {
    private int imagen;
    private String nombre;
    private int visitas;
    private String prueba;

    public Anime(int imagen, String nombre, int visitas, String prueba) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.visitas = visitas;
        this.prueba = prueba;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVisitas() {
        return visitas;
    }

    public int getImagen() {
        return imagen;
    }

    public String getPrueba() {
        return prueba;
    }
}
