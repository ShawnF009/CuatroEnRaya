package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public boolean estaVacio() {
        for (int j = 0; j < COLUMNAS; j++) {
            if (!columnaVacia(j)) return false;
        }
        return true;
    }

    private boolean columnaVacia(int columna) {
        return !casillas[0][columna].estaOcupada();
    }

    public boolean estaLleno() {
        for (int j = 0; j < COLUMNAS; j++) {
            if (!columnaLlena(j)) return false;
        }
        return true;
    }

    public boolean columnaLlena(int columna) {
        return casillas[FILAS - 1][columna].estaOcupada();
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws CuatroEnRayaExcepcion {
        comprobarColumna(columna);
        comprobarFicha(ficha);
        if (columnaLlena(columna)) {
            throw new CuatroEnRayaExcepcion("La columna estÃ¡ llena.");
        }
        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);
        return comprobarTirada(fila, columna, ficha);
    }

    private void comprobarFicha(Ficha ficha) {
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna) {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) {
        for (int i = 0; i < FILAS; i++) {
            if (!casillas[i][columna].estaOcupada()) return i;
        }
        return -1;
    }

    private boolean comprobarTirada(int fila, int columna, Ficha ficha) {
        return comprobarHorizontal(fila, ficha) ||
                comprobarVertical(columna, ficha) ||
                comprobarDiagonalNE(fila, columna, ficha) ||
                comprobarDiagonalNO(fila, columna, ficha);
    }

    private boolean objetivoAlcanzado(int contador) {
        return contador >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int contador = 0;
        for (int j = 0; j < COLUMNAS; j++) {
            contador = (casillas[fila][j].getFicha() == ficha) ? contador + 1 : 0;
            if (objetivoAlcanzado(contador)) return true;
        }
        return false;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int contador = 0;
        for (int i = 0; i < FILAS; i++) {
            contador = (casillas[i][columna].getFicha() == ficha) ? contador + 1 : 0;
            if (objetivoAlcanzado(contador)) return true;
        }
        return false;
    }

    private boolean comprobarDiagonalNE(int fila, int columna, Ficha ficha) {
        int desplazamiento = menor(fila, columna);
        int f = fila - desplazamiento;
        int c = columna - desplazamiento;
        int contador = 0;
        while (f < FILAS && c < COLUMNAS) {
            contador = (casillas[f][c].getFicha() == ficha) ? contador + 1 : 0;
            if (objetivoAlcanzado(contador)) return true;
            f++; c++;
        }
        return false;
    }

    private boolean comprobarDiagonalNO(int fila, int columna, Ficha ficha) {
        int desplazamiento = menor(fila, COLUMNAS - 1 - columna);
        int f = fila - desplazamiento;
        int c = columna + desplazamiento;
        int contador = 0;
        while (f < FILAS && c >= 0) {
            contador = (casillas[f][c].getFicha() == ficha) ? contador + 1 : 0;
            if (objetivoAlcanzado(contador)) return true;
            f++; c--;
        }
        return false;
    }

    private int menor(int a, int b) {
        return Math.min(a, b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = FILAS - 1; i >= 0; i--) {
            sb.append("|");
            for (int j = 0; j < COLUMNAS; j++) {
                sb.append(casillas[i][j]);
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}
