package org.iesalandalus.programacion.cuatroenraya.modelo;

import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class CuatroEnRaya {
    private Tablero tablero;
    private Jugador[] jugadores;
    private static final int NUMERO_JUGADORES = 2;

    public CuatroEnRaya(Jugador jugador1, Jugador jugador2) {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1.equals(jugador2)) {
            throw new IllegalArgumentException("Los jugadores deben ser distintos");
        }

        this.jugadores = new Jugador[NUMERO_JUGADORES];
        this.jugadores[0] = jugador1;
        this.jugadores[1] = jugador2;

        this.tablero = new Tablero(); // según tu implementación de Tablero [web:1][web:4]
    }

    public void jugar() {
        int indiceJugador = 0;
        boolean ganador = false;

        while (!tablero.estaLleno() && !ganador) {  // suponiendo estos métodos
            Jugador actual = jugadores[indiceJugador];
            ganador = tirar(actual);

            if (!ganador) {
                indiceJugador = (indiceJugador + 1) % NUMERO_JUGADORES;
            }
        }

        if (ganador) {
            System.out.println("Ha ganado " + jugadores[indiceJugador].nombre());
        } else {
            System.out.println("No quedan casillas libres. Empate.");
        }
    }

    private boolean tirar(Jugador jugador) {
        boolean jugadaValida = false;
        boolean ganador = false;

        do {
            int columna = Consola.leerColumna(jugador);  // según el enunciado de la práctica [web:1][web:4]
            try {
                ganador = tablero.introducirFicha(columna, jugador.validarColorFichas());
                jugadaValida = true;
            } catch (CuatroEnRayaExcepcion e) {
                System.out.println("Columna llena, elige otra columna.");
            } catch (CuatroEnRayaExcepcion e) {
                System.out.println("Columna inválida, intenta de nuevo.");
            }
        } while (!jugadaValida && !ganador);

        return ganador;
    }
}

