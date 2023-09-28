package Interfaz;


import Logica.Tablero;
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

        consolaUI.mostrarMensaje("Desea jugar una partida? (s/n)");
        consolaUI.mostrarMensaje(" ");
        String respuesta = scanner.nextLine().toLowerCase();
        consolaUI.mostrarMensaje(" ");
        jugarNuevaPartida = respuesta.equals("s");
        consolaUI.mostrarMensaje(" ");

        Tablero tablero = new Tablero();

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
                // Mostrar el tablero antes de que el jugador ingrese un movimiento
                if (consolaUI.primeraVez) {
                    consolaUI.actualizarTablero(tablero.getElementos());
                    consolaUI.primeraVez = false;
                } else {
                    consolaUI.mostrarTableros(tablero.getTableroAnterior(), tablero.getElementos());
                }

                String opcionMovimiento = scanner.nextLine();
                int fila = consolaUI.obtenerFilaDesdeEntrada(opcionMovimiento);
                int columna = consolaUI.obtenerColumnaDesdeEntrada(opcionMovimiento);
                String opcionMovimientoSinEspacios = opcionMovimiento.replace(" ", ""); // Eliminar espacios en blanco
                opcion = consolaUI.obtenerOpcionDesdeEntrada(opcionMovimientoSinEspacios);

                // Realizar el movimiento
                tablero.realizarMovimiento(fila - 1, columna -1, opcion);
                 System.out.println(fila + " " + columna);
            }

            consolaUI.mostrarMensaje(" ");
            consolaUI.mostrarMensaje("El juego ha terminado.");
            consolaUI.mostrarMensaje(tablero.mostrarResultado());
            consolaUI.mostrarMensaje("Desea jugar otra partida? (s/n)");
            respuesta = scanner.nextLine().toLowerCase();
            jugarNuevaPartida = respuesta.equals("s");

            if (jugarNuevaPartida) {
                tablero.reiniciarTablero();
            }
        }

        consolaUI.mostrarMensaje("Gracias por jugar a Soliflips. Hasta luego!");
    }

    // Agrega aquí las funciones obtenerFilaDesdeEntrada, obtenerColumnaDesdeEntrada y obtenerOpcionDesdeEntrada
}