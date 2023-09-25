package Interfaz;

import Logica.Tablero;
import java.util.Scanner;

// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(PONER NUMERO)

public class Sistema {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsolaUI consolaUI = new ConsolaUI();
        consolaUI.mostrarMensaje("-----------------------");
        consolaUI.mostrarMensaje("Bienvenido a Soliflips!");
        consolaUI.mostrarMensaje("-----------------------");

        boolean jugarNuevaPartida = true;
        
        while (jugarNuevaPartida) {
            consolaUI.mostrarMensaje("Seleccione una opcion:");
            consolaUI.mostrarMensaje("a) Tomar datos del archivo 'datos.txt'");
            consolaUI.mostrarMensaje("b) Usar el tablero predefinido");
            consolaUI.mostrarMensaje("c) Usar un tablero al azar");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "a":
                    // Cargar datos desde el archivo 'datos.txt' (implementa esta opción)
                    // Luego configura el juego y juega
                    // Ejemplo: cargarDatosDesdeArchivo(consolaUI, tablero);
                    break;
                case "b":
                    // Configura el tablero predefinido y juega
                    // Ejemplo: configurarTableroPredefinido(consolaUI, tablero);
                    break;
                case "c":
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
                         break;
                default:
                    consolaUI.mostrarMensaje("Opción no válida. Por favor, seleccione a, b o c.");
                    break;
            }

           

            // Preguntar si desea jugar una nueva partida
            consolaUI.mostrarMensaje("¿Desea jugar una nueva partida? (s/n)");
            String respuesta = scanner.nextLine().toLowerCase();
            jugarNuevaPartida = respuesta.equals("s");
        }

        consolaUI.cerrarScanner();
    }

    
}