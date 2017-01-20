package com.market_pymes.model;

/**
 * Created by sleyterangulo on 1/16/17.
 */

public class CxP {
    private int idFac;
    private int fecPago;
    private String proveedor;
    private float deud;
    private float cuot;
    private float abono;
    private float sald;

    public CxP() {

    }

    public int getIdFac() {
        return idFac;
    }

    public void setIdFac(int idFac) {
        this.idFac = idFac;
    }

    public int getFecPago() {
        return fecPago;
    }

    public void setFecPago(int fecPago) {
        this.fecPago = fecPago;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public float getDeud() {
        return deud;
    }

    public void setDeud(float deud) {
        this.deud = deud;
    }

    public float getCuot() {
        return cuot;
    }

    public void setCuot(float cuot) {
        this.cuot = cuot;
    }

    public float getAbono() {
        return abono;
    }

    public void setAbono(float abono) {
        this.abono = abono;
    }

    public float getSald() {
        return sald;
    }

    public void setSald(float sald) {
        this.sald = sald;
    }
}
