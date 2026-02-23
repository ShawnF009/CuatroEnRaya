package org.iesalandalus.programacion.cuatroenraya.modelo;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;

    private final Casilla[][] casillas;


    public Tablero() {

        this.casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }


    private boolean columnaVacia(int columna) {
        return !casillas[0][columna].estaOcupada();
    }

    public boolean estaVacio() {

        boolean tableroVacio = true;
        for (int j = 0; j < COLUMNAS; j++) {
            if (!columnaVacia(j)) {
                tableroVacio = false;
            }
        }
        return tableroVacio;
    }
    private boolean columnaLlena(int columna) {
        return casillas[FILAS - 1][columna].estaOcupada();
    }

    public boolean estaLleno() {
        boolean tableroLleno = true; // inicia lleno
        for (int j = 0; j < COLUMNAS; j++) {
            if (!columnaLlena(j)) {
                tableroLleno = false;
            }
        }
        return tableroLleno;
    }


    private void comprobarFicha(Ficha ficha) {
        if (ficha == null)
            throw new NullPointerException("La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna) {
        if (columna < 0 || columna == 7)
            throw new IllegalArgumentException("Columna incorrecta.");
    }

    private int getPrimeraFilaVacia(int columna) {
        for (int i = 0; i < 6; i++) {
            if (!casillas[i][columna].estaOcupada()) {
                return i;
            }
        }
        return -1;
    }
    private boolean objetoAlcanzado(int fichasIgualesConsecutivas) {
        return fichasIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }
    public boolean introducirFicha(int columna, Ficha ficha) throws CuatroEnRayaExcepcion {
        comprobarColumna(columna);
        comprobarFicha(ficha);
        if (columnaLlena(columna)) {
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }
        int filaLibre = 0;
        while(filaLibre < FILAS && casillas[filaLibre][columna].estaOcupada()){
            filaLibre++;
        }
        casillas[filaLibre][columna].setFicha(ficha);
        boolean ganadora = comprobarTirada(filaLibre, columna, ficha);

        return ganadora;
    }
    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int contador = 0;
        boolean hayCuatro = false;
        for(int j = 0; j < COLUMNAS; j++){
            if(casillas[fila][j].getFicha() == ficha){
                contador ++;
                if (contador == 4){
                    hayCuatro = true;
                }
            }else {
                contador = 0;
            }
        }
        return hayCuatro;
    }
    private boolean comprobarVertical(int columna, Ficha ficha) {
        int contador = 0;
        boolean hayCuatro = false;
        for(int i = 0; i < FILAS; i++){
            if(casillas[i][columna].getFicha() == ficha){
                contador ++;
                if (contador == 4){
                    hayCuatro = true;
                }
            }else {
                contador = 0;
            }
        }
        return hayCuatro;
    }
    private int menor(int fila, int columna) {
        return  (fila < columna) ? fila : columna;
    }

    private boolean comprobarDiagonalINE(int filaActual, int columnaActual, Ficha ficha) {
        int desplazamiento = menor(filaActual, columnaActual);
        int fila = filaActual - desplazamiento;
        int columna = columnaActual - desplazamiento;
        int contador = 0;
        boolean hayCuatro = false;
        while(fila < FILAS && columna < COLUMNAS){
            if(casillas[fila][columna].estaOcupada() && casillas[fila][columna].getFicha() == ficha){
                contador ++;
                if (contador == 4){
                    hayCuatro = true;
                }
            }else {
                contador = 0;
            }
            fila++;
            columna++;
        }
        return hayCuatro;
    }
    private boolean comprobarDiagonalINO(int filaActual, int columnaActual, Ficha ficha) {
        int desplazamiento = menor(filaActual, COLUMNAS - 1 - columnaActual);
        int fila = filaActual - desplazamiento;
        int columna = columnaActual + desplazamiento;
        int contador = 0;
        boolean hayCuatro = false;
        while(fila < FILAS && columna >= 0){
            if(casillas[fila][columna].estaOcupada() && casillas[fila][columna].getFicha() == ficha){
                contador ++;
                if (contador == 4){
                    hayCuatro = true;
                }
            }else {
                contador = 0;
            }
            fila++;
            columna--;
        }
        return hayCuatro;
    }
    private boolean comprobarTirada(int fila, int columna, Ficha ficha) {
        boolean ganadora = false;
        if (comprobarHorizontal(fila, ficha)){
            ganadora = true;
        }
        if (comprobarVertical(columna, ficha)) {
            ganadora = true;
        }
        if (comprobarDiagonalINE(fila, columna,ficha)){
            ganadora = true;
        }
        if (comprobarDiagonalINO(fila, columna, ficha)){
            ganadora = true;
        }
        return ganadora;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        for (int fila = FILAS - 1; fila >= 0; fila--) {
            sb.append("|");

            for (int col = 0; col < COLUMNAS; col++) {

                if (casillas[fila][col].estaOcupada()) {
                    sb.append(casillas[fila][col].getFicha().toString());
                } else {
                    sb.append(" ");
                }
            }

            sb.append("|\n");
        }
        sb.append(" ");
        sb.append("-".repeat(COLUMNAS));
        sb.append("\n");

        return sb.toString();
    }
}