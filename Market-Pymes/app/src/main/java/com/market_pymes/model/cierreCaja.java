package com.market_pymes.model;

/**
 * Created by sleyterangulo on 1/23/17.
 */

public class cierreCaja {
    private float cajaInicial;
    private float factContado;
    private float factCredito;
    private float servContado;
    private float servCredito;
    private float gastos;

    public cierreCaja() {
    }

    public float getCajaInicial() {
        return cajaInicial;
    }

    public void setCajaInicial(float cajaInicial) {
        this.cajaInicial = cajaInicial;
    }

    public float getFactContado() {
        return factContado;
    }

    public void setFactContado(float factContado) {
        this.factContado = factContado;
    }

    public float getFactCredito() {
        return factCredito;
    }

    public void setFactCredito(float factCredito) {
        this.factCredito = factCredito;
    }

    public float getServContado() {
        return servContado;
    }

    public void setServContado(float servContado) {
        this.servContado = servContado;
    }

    public float getServCredito() {
        return servCredito;
    }

    public void setServCredito(float servCredito) {
        this.servCredito = servCredito;
    }

    public float getGastos() {
        return gastos;
    }

    public void setGastos(float gastos) {
        this.gastos = gastos;
    }
}
