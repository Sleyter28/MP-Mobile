package com.market_pymes.model;

/**
 * Created by sleyterangulo on 1/20/17.
 */

public class Inventario {
    private String nomProducto;
    private String catProducto;
    private String desProducto;
    private float precProducto;
    private int cantProducto;

    public Inventario() {
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getCatProducto() {
        return catProducto;
    }

    public void setCatProducto(String catProducto) {
        this.catProducto = catProducto;
    }

    public String getDesProducto() {
        return desProducto;
    }

    public void setDesProducto(String desProducto) {
        this.desProducto = desProducto;
    }

    public float getPrecProducto() {
        return precProducto;
    }

    public void setPrecProducto(float precProducto) {
        this.precProducto = precProducto;
    }

    public int getCantProducto() {
        return cantProducto;
    }

    public void setCantProducto(int cantProducto) {
        this.cantProducto = cantProducto;
    }
}
