package Interfaz;

import Logica.Tablero;
import java.util.Scanner;

// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(PONER NUMERO)

public class Sistema {
  
    
    public static void main(String[] args) {
    boolean jugarNuevaPartida = true;
    Scanner scanner = new Scanner(System.in);

    ConsolaUI consolaUI = new ConsolaUI();
    consolaUI.mostrarMensaje("-----------------------");
    consolaUI.mostrarMensaje("Bienvenido a Soliflips!");
    consolaUI.mostrarMensaje("-----------------------");
    consolaUI.mostrarMensaje(" ");

    Tablero tablero = new Tablero();

    while (jugarNuevaPartida) {
        consolaUI.mostrarMensaje("Seleccione una opcion:");
        consolaUI.mostrarMensaje("a) Tomar datos del archivo 'datos.txt'");
        consolaUI.mostrarMensaje("b) Usar el tablero predefinido");
        consolaUI.mostrarMensaje("c) Usar un tablero al azar");
        consolaUI.mostrarMensaje(" ");
        
        String opcion = scanner.nextLine().toLowerCase();

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

        // En este codigo ahorramos el while y no repetimos para cada caso del Switch
        while (tablero.estaEnProgreso()) {
            consolaUI.mostrarMensaje("Estado actual del tablero:");
            consolaUI.actualizarTablero(tablero.getElementos());
            String[] coordenadas = consolaUI.leerMovimiento("Ingrese las coordenadas separadas por un espacio");
            int fila = Integer.parseInt(coordenadas[0]) - 1;
            int columna = Integer.parseInt(coordenadas[1]) - 1;

            tablero.realizarMovimiento(fila, columna);
            consolaUI.mostrarMensaje(tablero.obtenerPasosNecesariosParaGanar());

            if (!tablero.estaEnProgreso()) {
                String resultado = tablero.mostrarResultado();
                consolaUI.mostrarMensaje(resultado);
            }
        }

        // Preguntar si desea jugar una nueva partida
        consolaUI.mostrarMensaje("Desea jugar una nueva partida? (s/n)");
        consolaUI.mostrarMensaje(" ");
        String respuesta = scanner.nextLine().toLowerCase();

        jugarNuevaPartida = respuesta.equals("s");
    }

    consolaUI.cerrarScanner();
}


    
}