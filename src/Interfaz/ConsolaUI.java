// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(256909)

package Interfaz;

import Logica.Celda;
import Logica.Coordenada;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ConsolaUI {
    private Scanner scanner;
    public boolean primeraVez = true;
    public Celda[][] tableroAnterior;

    public ConsolaUI() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void actualizarTablero(Celda[][] elementos) {
        int numRows = elementos.length;
        int numCols = elementos[0].length;

        
        System.out.print(" ");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.printf("%4d", columna + 1);
        }
        System.out.println();

       
        for (int fila = 0; fila < numRows; fila++) {
           
            System.out.print("  +");
            for (int columna = 0; columna < numCols; columna++) {
                System.out.print("---+");
            }
            System.out.println();

           
            System.out.printf("%2d|", fila + 1);

            for (int columna = 0; columna < numCols; columna++) {
                char simbolo = elementos[fila][columna].getSimbolo();
                char color = elementos[fila][columna].getColor();

               
                String colorCode = (color == 'R') ? "\u001B[31m" : "\u001B[34m"; 

              
                String resetColor = "\u001B[0m";

               
                System.out.print(" " + colorCode + simbolo + resetColor + " |");
            }

            
            System.out.println();
        }

       
        System.out.print("  +");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.print("---+");
        }
        System.out.println();
    }

   public void mostrarTableros(Celda[][] tableroAnterior, Celda[][] tableroActual) {
    int numRows = tableroActual.length;
    int numCols = tableroActual[0].length;

   
    System.out.print(" ");
    for (int columna = 0; columna < numCols; columna++) {
        System.out.printf("%4d", columna + 1);
    }
    System.out.print("        "); 
    for (int columna = 0; columna < numCols; columna++) {
        System.out.printf("%4d", columna + 1);
    }
    System.out.println(); 

    for (int fila = 0; fila < numRows; fila++) {
        
        System.out.print("  +");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.print("---+");
        }
        System.out.print("  ==>"); 
       
        System.out.print("  +");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.print("---+");
        }
        System.out.println();

      
        System.out.printf("%2d|", fila + 1);
        for (int columna = 0; columna < numCols; columna++) {
            char simboloAnterior = tableroAnterior[fila][columna].getSimbolo();
            char colorAnterior = tableroAnterior[fila][columna].getColor();
            String colorCodeAnterior = (colorAnterior == 'R') ? "\u001B[31m" : "\u001B[34m"; 
            String resetColor = "\u001B[0m"; 
            System.out.print(" " + colorCodeAnterior + simboloAnterior + resetColor + " |");
        }
        if (fila <= numRows) {
            System.out.print("  ==>");
        }else{
        System.out.print("  ==>"); 
        }
       
        System.out.printf("%2d|", fila + 1);
        for (int columna = 0; columna < numCols; columna++) {
            char simboloActual = tableroActual[fila][columna].getSimbolo();
            char colorActual = tableroActual[fila][columna].getColor();
            String colorCodeActual = (colorActual == 'R') ? "\u001B[31m" : "\u001B[34m"; 
            String resetColor = "\u001B[0m"; 
            System.out.print(" " + colorCodeActual + simboloActual + resetColor + " |");
        }

        System.out.println(); 
    }

   
    System.out.print("  +");
    for (int columna = 0; columna < numCols; columna++) {
        System.out.print("---+");
    }

    System.out.print("  ==>"); 

    
    System.out.print("  +");
    for (int columna = 0; columna < numCols; columna++) {
        System.out.print("---+");
    }
    System.out.println(); 
}



    public int leerEntero(String mensaje) {
        int valor = 0;
        boolean entradaValida = false;
        do {
            try {
                System.out.println(mensaje);
                valor = Integer.parseInt(scanner.nextLine());
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entero.");
            }
        } while (!entradaValida);
        return valor;
    }

    
    
    public static boolean esNumero(String entrada) {
        try {
            Integer.parseInt(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public String mostrarHistoriaMovimientos(ArrayList <Coordenada> movimientosRealizados) {
        String historialMovimientos = "";
        for (Coordenada movimiento : movimientosRealizados) {
            historialMovimientos += ("(" + (movimiento.getFila()) + ", " + (movimiento.getColumna()) + ")" +" ");
        }
            return "Los movimientos Realizados son: " + historialMovimientos;
    }
    
     public String mostrarSecuenciaMovimientos(ArrayList <Coordenada> caminoSolucion) {
        String secuenciaSolucion = "";
               
        
        for (Coordenada pasoSolucion : caminoSolucion){
            secuenciaSolucion += ("(" + (pasoSolucion.getFila()) + ", " + (pasoSolucion.getColumna()) + ")" +" ");
        }
        
        return "Los movimientos para ganar son: " + secuenciaSolucion;
    }
    
  

    public void cerrarScanner() {
        scanner.close();
    }
}