package Interfaz;

import Logica.Tablero;
import Logica.Celda;
import java.util.Arrays;
import java.util.Scanner;

public class Sistema {
    public static void main(String[] args) {
        boolean jugarNuevaPartida = true;
        Scanner scanner = new Scanner(System.in);

        ConsolaUI consolaUI = new ConsolaUI();
        consolaUI.mostrarMensaje("-----------------------");
        consolaUI.mostrarMensaje("Bienvenido a Soliflips!");
        consolaUI.mostrarMensaje("-----------------------");
        consolaUI.mostrarMensaje(" ");
        
        consolaUI.mostrarMensaje("Desea jugar una  partida? (s/n)");
        consolaUI.mostrarMensaje(" ");
        String respuesta = scanner.nextLine().toLowerCase();
        consolaUI.mostrarMensaje(" ");
        jugarNuevaPartida = respuesta.equals("s");
        consolaUI.mostrarMensaje(" ");

        Tablero tablero = new Tablero();
        Celda[][] tableroAnterior = tablero.getElementos(); // Almacenar el tablero anterior

        while (jugarNuevaPartida) {
            consolaUI.mostrarMensaje("Seleccione una opcion:");
            consolaUI.mostrarMensaje("a) Tomar datos del archivo 'datos.txt'");
            consolaUI.mostrarMensaje("b) Usar el tablero predefinido");
            consolaUI.mostrarMensaje("c) Usar un tablero al azar");
            consolaUI.mostrarMensaje(" ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "a":
                    tablero.cargarDatosDesdeArchivo();
                    break;
                case "b":
                    tablero.configurarTableroPredefinido();
                    break;
                case "c":
                    int filas = consolaUI.leerEntero("Ingrese cantidad de filas: ");
                    int columnas = consolaUI.leerEntero("Ingrese cantidad de Columnas: ");
                    int nivel = consolaUI.leerEntero("Ingrese el nivel del juego: ");
                    consolaUI.mostrarMensaje(" ");
                    tablero.configurarJuego(filas, columnas, nivel);
                    break;
                default:
                    consolaUI.mostrarMensaje("Opcion no valida. Por favor, seleccione a, b o c.");
                    consolaUI.mostrarMensaje(" ");
                    continue;
            }

            // Bucle while que actualiza los tableros
            consolaUI.primeraVez = true;
            while (tablero.estaEnProgreso()) {
                if (consolaUI.primeraVez) {
        // Crear una copia independiente del tablero actual
        tableroAnterior = new Celda[tablero.getElementos().length][];
        for (int i = 0; i < tablero.getElementos().length; i++) {
            tableroAnterior[i] = new Celda[tablero.getElementos()[i].length];
            for (int j = 0; j < tablero.getElementos()[i].length; j++) {
                char simboloActual = tablero.getElementos()[i][j].getSimbolo();
                char colorActual = tablero.getElementos()[i][j].getColor();
                tableroAnterior[i][j] = new Celda(simboloActual, colorActual);
            }
        }
        
        consolaUI.actualizarTablero(tablero.getElementos());
        consolaUI.primeraVez = false;
    } else {
        consolaUI.mostrarTableros(tableroAnterior, tablero.getElementos());
    }

                String[] coordenadas = consolaUI.leerMovimiento("Ingrese las coordenadas separadas por un espacio");
                int fila = Integer.parseInt(coordenadas[0]) - 1;
                int columna = Integer.parseInt(coordenadas[1]) - 1;
                
              
                tablero.realizarMovimiento(fila, columna);
               tableroAnterior = new Celda[tablero.getElementos().length][];
    for (int i = 0; i < tablero.getElementos().length; i++) {
        tableroAnterior[i] = Arrays.copyOf(tablero.getElementos()[i], tablero.getElementos()[i].length);
    }
                
                                
                if (!tablero.estaEnProgreso()) {
                    String resultado = tablero.mostrarResultado();
                    consolaUI.mostrarMensaje(resultado);
                }
                // Actualizar el tablero anterior después de cada movimiento
              
            }

            // Preguntar si desea jugar una nueva partida
            jugarNuevaPartida = false;
            consolaUI.mostrarMensaje("Desea jugar una nueva partida? (s/n)");
            consolaUI.mostrarMensaje(" ");
            respuesta = scanner.nextLine().toLowerCase();

            jugarNuevaPartida = respuesta.equals("s");
        }

        consolaUI.cerrarScanner();
    }
}
