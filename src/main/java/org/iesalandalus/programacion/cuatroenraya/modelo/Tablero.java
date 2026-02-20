package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    public Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];

        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                casillas[i][j] = new Casilla();
            }
        }
    }

    private boolean columnaVacia(int columna) {
        return !(casillas[0][columna].estaOcupada());
    }

    public boolean estaVacio() {
        int vacios = 0;

        for (int i = 0; i < COLUMNAS; i++){
            if (columnaVacia(i)){
                vacios++;
            }
        }

        return (vacios == COLUMNAS);
    }

    private boolean columnaLlena(int columna) {
        return casillas[FILAS-1][columna].estaOcupada();
    }

    public boolean estaLleno()  {
        int llenos = 0;

        for (int i = 0; i < COLUMNAS; i++){
            if (columnaLlena(i)){
                llenos++;
            }
        }

        return (llenos == COLUMNAS);
    }

    private void comprobarFicha(Ficha ficha){
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna){
        if (columna < 0 || columna >= COLUMNAS){
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFila(int columna)  {
        int i = -1;

        do {
            i++;
        } while (casillas[i][columna].estaOcupada());

        return i;
    }

    public boolean introducirFicha(int columna,Ficha ficha) throws CuatroEnRayaExcepcion {
        comprobarColumna(columna);
        comprobarFicha(ficha);

        if(columnaLlena(columna)){
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }

        int fila = getPrimeraFila(columna);

        casillas[fila][columna].setFicha(ficha);

        return comprobarTirada(fila,columna);
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas){
        return (fichasIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS);
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int racha = 0;

        for (int i = 0; i < COLUMNAS && !objetivoAlcanzado(racha); i++){
            if (casillas[fila][i].estaOcupada() && ficha.equals(casillas[fila][i].getFicha())){
                racha++;
            } else {
                racha = 0;
            }
        }

        return objetivoAlcanzado(racha);
    }

    private boolean comprobarVertical(int columna, Ficha ficha){
        int racha = 0;

        for (int i = 0; i < FILAS && !objetivoAlcanzado(racha); i++){
            if (casillas[i][columna].estaOcupada() && ficha.equals(casillas[i][columna].getFicha())){
                racha++;
            } else {
                racha = 0;
            }
        }

        return objetivoAlcanzado(racha);
    }

    private boolean comprobarDiagonalNE(int filaActual, int columnaActual, Ficha ficha) {

        int racha = 0;
        int desplazamiento = menor(filaActual, columnaActual);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual - desplazamiento;
        for (int i = filaInicial, j = columnaInicial; !objetivoAlcanzado(racha) && i < FILAS && j < COLUMNAS; i++, j++) {
            if (casillas[i][j].estaOcupada() && ficha.equals(casillas[i][j].getFicha())) {
                racha++;
            } else{
                racha = 0;
            }
        }
        return objetivoAlcanzado(racha);
    }
    private boolean comprobarDiagonalNO(int filaActual, int columnaActual, Ficha ficha) {

        int racha = 0;
        int desplazamiento = menor(filaActual, COLUMNAS - columnaActual - 1);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual + desplazamiento;
        for (int i = filaInicial, j = columnaInicial; !objetivoAlcanzado(racha) && i < FILAS && j >= 0; i++, j--) {
            if (casillas[i][j].estaOcupada() && ficha.equals(casillas[i][j].getFicha())) {
                racha++;
            } else{
                racha = 0;
            }
        }
        return objetivoAlcanzado(racha);
    }

    private int menor(int fila,int columna){
        int menor;

        if (fila < columna){
            menor = fila;
        } else {
            menor = columna;
        }

        return menor;
    }

    private boolean comprobarTirada(int fila, int columna) {
        return (comprobarHorizontal(fila, casillas[fila][columna].getFicha()) || comprobarVertical(columna, casillas[fila][columna].getFicha()) ||  comprobarDiagonalNE(fila,columna, casillas[fila][columna].getFicha()) || comprobarDiagonalNO(fila,columna, casillas[fila][columna].getFicha()));
    }

    @Override
    public String toString() {
        String fila = "|" ;


        StringBuilder tableroSB = new StringBuilder();

        for (int j = FILAS-1; j >= 0; j--) {
            tableroSB.append(fila);
            for (int i = 0; i < COLUMNAS; i++) {
                tableroSB.append(casillas[j][i].toString());
            }
            tableroSB.append(fila);
            tableroSB.append("\n");
        }

        tableroSB.append(" ");

        for (int i = 0; i < COLUMNAS; i++) {
            tableroSB.append("-");
        }

        tableroSB.append("\n");

        System.out.println(tableroSB);

        return tableroSB.toString();
    }
}