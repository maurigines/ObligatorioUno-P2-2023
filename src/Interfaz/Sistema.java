package Interfaz;

import Logica.Tablero;

// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(PONER NUMERO)

public class Sistema {

    public static void main(String[] args) {
        ConsolaUI consolaUI = new ConsolaUI();
        consolaUI.mostrarMensaje("Bienvenido a Soliflips!");

        int filas = consolaUI.leerEntero("Ingrese el numero de filas del tablero: ");
        int columnas = consolaUI.leerEntero("Ingrese el numero de columnas del tablero: ");
        int nivel = consolaUI.leerEntero("Ingrese el nivel del juego: ");

        Tablero tablero = new Tablero();
        tablero.configurarJuego(filas, columnas, nivel);

        while (tablero.estaEnProgreso()) {
            consolaUI.mostrarMensaje("Estado actual del tablero:");
            consolaUI.actualizarTablero(tablero.getElementos());

            int fila = consolaUI.leerEntero("Ingrese la Fila: ") - 1;
            int columna = consolaUI.leerEntero("Ingrese la Columna: ") - 1;


            tablero.realizarMovimiento(fila, columna);
            consolaUI.mostrarMensaje(tablero.obtenerPasosNecesariosParaGanar());

            if (!tablero.estaEnProgreso()) {
                String resultado = tablero.mostrarResultado();
                consolaUI.mostrarMensaje(resultado);
            }
        }

        consolaUI.cerrarScanner();
    }
}
