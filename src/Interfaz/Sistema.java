// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(256909)

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
        
        while (!(respuesta.equals("s") || respuesta.equals("n"))) {
            consolaUI.mostrarMensaje("Ingrese una respuesta valida s/n ");
            respuesta = scanner.nextLine().toLowerCase();
            jugarNuevaPartida = respuesta.equals("s");
            consolaUI.mostrarMensaje(" ");
           
            
        }
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
                    tablero.copiarTableroActualATableroAnterior();
                    break;
                case "b":
                    tablero.configurarTableroPredefinido();
                    tablero.copiarTableroActualATableroAnterior();
                    break;
                case "c":
                    int filas,
                     columnas,
                     nivel;

                    do {
                        filas = consolaUI.leerEntero("Ingrese la cantidad de filas (entre 3 y 9): ");
                    } while (filas < 3 || filas > 9);

                    do {
                        columnas = consolaUI.leerEntero("Ingrese la cantidad de columnas (entre 3 y 9): ");
                    } while (columnas < 3 || columnas > 9);

                    do {
                        nivel = consolaUI.leerEntero("Ingrese el nivel (entre 1 y 8): ");
                    } while (nivel < 1 || nivel > 8);
                    consolaUI.mostrarMensaje(" ");
                    
                                      
                    tablero.configurarJuego(filas, columnas, nivel);
                    
                    
                    break;
                default:
                    consolaUI.mostrarMensaje("Opcion no valida. Por favor, seleccione a, b o c.");
                    consolaUI.mostrarMensaje(" ");
                    continue;
            }

           
            consolaUI.primeraVez = true;
           
            while (tablero.estaEnProgreso()) {
                
                if (consolaUI.primeraVez) {
                    consolaUI.actualizarTablero(tablero.getElementos());
                    consolaUI.primeraVez = false;
                } 
                    
                
                   

                consolaUI.mostrarMensaje("Ingrese fila o seleccione X, H, o S:");
                
                String opcionMovimiento = scanner.nextLine();
                int fila = 0;
                int columna = 0;

               
                if (opcionMovimiento.length() == 1 && Character.isLetter(opcionMovimiento.charAt(0))) {
                    char letra = opcionMovimiento.toUpperCase().charAt(0);

                    switch (letra) {
                        case 'X': 
                            tablero.terminarJuego();
                            break;
                        case 'H':
                            consolaUI.mostrarMensaje(consolaUI.mostrarHistoriaMovimientos(tablero.mostrarHistoriaMovimientos()));
                            break;
                        case 'S':
                           consolaUI.mostrarMensaje(consolaUI.mostrarSecuenciaMovimientos(tablero.mostrarSecuenciaMovimientos()));
                            break;
                        default:
                            consolaUI.mostrarMensaje("Opcion invalida para la letra ingresada.");
                    }
                } 
                else if (consolaUI.esNumero(opcionMovimiento)) {
                    int numero = Integer.parseInt(opcionMovimiento);
                    fila = numero;
                    columna = consolaUI.leerEntero("Ingrese columna:");
                    
                    if((0<= fila && fila <= tablero.getNumFilas()) && (0<= columna && columna <= tablero.getNumColumnas())){
                        tablero.realizarMovimiento(fila - 1, columna -1);
                        tablero.almacenarMovimientoRealizado(fila, columna);
                        consolaUI.mostrarTableros(tablero.getTableroAnterior(), tablero.getElementos());
                    } else if((-1== fila && fila <= tablero.getNumFilas()) && (-1== columna && columna <= tablero.getNumColumnas())){
                        tablero.realizarMovimiento(fila - 1, columna -1);
                        tablero.almacenarMovimientoRealizado(fila, columna);
                        consolaUI.primeraVez = true;
                    }
                    else{
                        consolaUI.mostrarMensaje("Entrada invalida, ingrese una entrada acorde a " + tablero.getNumFilas()+" filas y " + tablero.getNumColumnas() + " columnas");
                    }
                    
                }
                else {
                    System.out.println("Opcion invalida.");
                }

               
            }

                
               
                
            

            consolaUI.mostrarMensaje(" ");
            consolaUI.mostrarMensaje("El juego ha terminado.");
            consolaUI.mostrarMensaje(tablero.mostrarResultado());
            consolaUI.mostrarMensaje("Desea jugar otra partida? (s/n)");
            respuesta = scanner.nextLine().toLowerCase();
            jugarNuevaPartida = respuesta.equals("s");
            while (!(respuesta.equals("s") || respuesta.equals("n"))) {
                consolaUI.mostrarMensaje("Ingrese una respuesta valida s/n ");
                respuesta = scanner.nextLine().toLowerCase();
                jugarNuevaPartida = respuesta.equals("s");
                consolaUI.mostrarMensaje(" ");

            }

            if (jugarNuevaPartida) {
                tablero.limpiarHistoriales();
                tablero.reiniciarTablero();
                
            }
        }

        consolaUI.mostrarMensaje("Gracias por jugar a Soliflips. Hasta luego!");
    }

    
}