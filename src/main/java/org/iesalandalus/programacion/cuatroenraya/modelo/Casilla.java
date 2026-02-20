package org.iesalandalus.programacion.cuatroenraya.modelo;


import java.util.Objects;

public class Casilla {
    private Ficha ficha;

    public Casilla(){
        this.ficha = null;
    }
    public Ficha getFicha(){
        return this.ficha;
    }
    public void setFicha(Ficha ficha){
        Objects.requireNonNull(ficha,"No se puede poner una ficha nula.");
        if (estaOcupada()){
            this.ficha = ficha;
        } else {
            throw new CuatroEnRayaExcepcion("La casilla ya contiene una ficha.");
        }
    }
    public boolean estaOcupada()throws CuatroEnRayaExcepcion{
        return ficha == null;
    }

    @Override
    public String toString() {
        return (ficha != null) ? String.format("%s", ficha): " ";
    }
}
