package com.market_pymes.model;

/**
 * Created by sleyterangulo on 1/23/17.
 */

public class Min {
    private String codProducto;
    private String catProducto;
    private String despProducto;
    private int cantProducto;
    private int limProducto;

    public Min() {
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getCatProducto() {
        return catProducto;
    }

    public void setCatProducto(String catProducto) {
        this.catProducto = catProducto;
    }

    public String getDespProducto() {
        return despProducto;
    }

    public void setDespProducto(String despProducto) {
        this.despProducto = despProducto;
    }

    public int getCantProducto() {
        return cantProducto;
    }

    public void setCantProducto(int cantProducto) {
        this.cantProducto = cantProducto;
    }

    public int getLimProducto() {
        return limProducto;
    }

    public void setLimProducto(int limProducto) {
        this.limProducto = limProducto;
    }
}
