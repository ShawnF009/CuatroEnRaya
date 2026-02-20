package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {
   private Casilla[][] casillas;

   public static final int FILAS = 6;
   public static final int COLUMNAS = 7;
   public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;

    public Tablero(){
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public boolean estaVacio(){
        boolean vacio = true;
        return vacio;
    }
    private boolean columnaVacia(int columna){
        return !casillas[0][columna].estaOcupada();
    }

    public boolean estaLleno(){
        boolean lleno = true;
        for (int i = 0; i < COLUMNAS && lleno; i++){
            lleno = columnaLlena(i);
        }
        return lleno;
    }

    private boolean columnaLlena(int columna){
        return casillas[FILAS - 1][columna].estaOcupada();
    }

    public boolean introducirFicha(int columna, Ficha ficha){
        comprobarFicha(ficha);
        comprobarColumna(columna);
        if (columnaLlena(columna)){
            throw new CuatroEnRayaExcepcion("Columna llena.");
        } else {
            int fila = getPrimeraFilaVacia(columna);
            casillas[fila][columna].setFicha(ficha);
            return comprobarTirada(fila,columna);
        }
    }

    private void comprobarFicha(Ficha ficha){
        if (ficha == null) {
            throw new NullPointerException("La ficha no puede ser nula.");
        }
    }

    private void comprobarColumna(int columna){
        if (columna < 0 || columna >= COLUMNAS){
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna){
        int fila;
        for (fila = 0; fila < FILAS && casillas[fila][columna].estaOcupada(); fila++){}
        return fila;
    }

    private boolean comprobarTirada(int fila, int columna){
        Ficha ficha = casillas[fila][columna].getFicha();
        return true;
    }
    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas){
        return true;
    }
    private boolean comprobarHorizontal(int fila, Ficha ficha){
        return true;
    }
    private boolean comprobarVertical(int columna, Ficha ficha){
        return true;
    }
    private boolean comprobarDiagonalNE(int filaActual, int columnaActual, Ficha ficha){
        return true;
    }
    private boolean comprobarDiagonalNO(int filaActual, int columnaActual, Ficha ficha){
        return true;
    }
    private int menor(int fila, int columna){
        return fila;
    }

}
