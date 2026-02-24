package org.iesalandalus.programacion.cuatroenraya.vista;

import org.iesalandalus.programacion.cuatroenraya.modelo.CuatroEnRayaExcepcion;
import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.modelo.Tablero;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

    public static String leerNombre() {
        String nombre;
        do {
            System.out.print("Introduce tu nombre: ");
        }
    }

    public static Ficha elegirColorFichas() {
        int eleccion;
        do {
            System.out.print("Elige el color de tus fichas ( 0-Azul, 1-Verde):");
            eleccion = Entrada.entero();
        } while (eleccion < 0 || eleccion > 1);
        return Ficha.values()[eleccion];
    }

    public static Jugador leerJugador(Ficha color) {
        System.out.println("Introduce los datos del primer jugador");
        return new Jugador(leerNombre(), elegirColorFichas());
    }

    public static int leerColumna(Jugador jugador) {
        int columna;
        do {
            System.out.print("%s, introduce la columna en la que deseas introducir la ficha (0 - %d): ", jugador.nombre(), Tablero.COLUMNAS - 1);
        columna = Entrada.entero();
        } while (columna < 0 || columna >= Tablero.COLUMNAS);
        return columna;
    }
}