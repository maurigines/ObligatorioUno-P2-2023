package Interfaz;

import Logica.Celda;
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

        // Imprimir números de columna centrados
        System.out.print(" ");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.printf("%4d", columna + 1);
        }
        System.out.println();

        // Imprimir filas
        for (int fila = 0; fila < numRows; fila++) {
            // Línea superior de la fila
            System.out.print("  +");
            for (int columna = 0; columna < numCols; columna++) {
                System.out.print("---+");
            }
            System.out.println();

            // Número de fila alineado a la derecha
            System.out.printf("%2d|", fila + 1);

            for (int columna = 0; columna < numCols; columna++) {
                char simbolo = elementos[fila][columna].getSimbolo();
                char color = elementos[fila][columna].getColor();

                // Utilizamos códigos de colores ANSI para establecer el color del texto
                String colorCode = (color == 'R') ? "\u001B[31m" : "\u001B[34m"; // Rojo o Azul

                // Restablecemos el color después del carácter
                String resetColor = "\u001B[0m";

                // Imprimir celda
                System.out.print(" " + colorCode + simbolo + resetColor + " |");
            }

            // Separación de filas
            System.out.println();
        }

        // Última línea inferior del tablero
        System.out.print("  +");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.print("---+");
        }
        System.out.println();
    }

   public void mostrarTableros(Celda[][] tableroAnterior, Celda[][] tableroActual) {
    int numRows = tableroActual.length;
    int numCols = tableroActual[0].length;

    // Imprime los números de columna centrados para ambos tableros
    System.out.print(" ");
    for (int columna = 0; columna < numCols; columna++) {
        System.out.printf("%4d", columna + 1);
    }
    System.out.print("        "); // Espacio entre los dos tableros
    for (int columna = 0; columna < numCols; columna++) {
        System.out.printf("%4d", columna + 1);
    }
    System.out.println(); // Cambio de línea

    for (int fila = 0; fila < numRows; fila++) {
        // Línea superior de la fila para el tablero anterior
        System.out.print("  +");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.print("---+");
        }
        System.out.print("     "); // Espacio entre los dos tableros
        // Línea superior de la fila para el tablero actual
        System.out.print("  +");
        for (int columna = 0; columna < numCols; columna++) {
            System.out.print("---+");
        }
        System.out.println(); // Cambio de línea

        // Número de fila alineado a la derecha para el tablero anterior
        System.out.printf("%2d|", fila + 1);
        for (int columna = 0; columna < numCols; columna++) {
            char simboloAnterior = tableroAnterior[fila][columna].getSimbolo();
            char colorAnterior = tableroAnterior[fila][columna].getColor();
            String colorCodeAnterior = (colorAnterior == 'R') ? "\u001B[31m" : "\u001B[34m"; // Rojo o Azul
            String resetColor = "\u001B[0m"; // Restablece el color después del carácter
            System.out.print(" " + colorCodeAnterior + simboloAnterior + resetColor + " |");
        }
        if (fila < numRows) {
            System.out.print("  ==>");
        }else{
        System.out.print("     "); // Espacio entre los dos tableros
        }
        // Número de fila alineado a la derecha para el tablero actual
        System.out.printf("%2d|", fila + 1);
        for (int columna = 0; columna < numCols; columna++) {
            char simboloActual = tableroActual[fila][columna].getSimbolo();
            char colorActual = tableroActual[fila][columna].getColor();
            String colorCodeActual = (colorActual == 'R') ? "\u001B[31m" : "\u001B[34m"; // Rojo o Azul
            String resetColor = "\u001B[0m"; // Restablece el color después del carácter
            System.out.print(" " + colorCodeActual + simboloActual + resetColor + " |");
        }

        System.out.println(); // Cambio de línea
    }

    // Línea inferior del tablero para el tablero anterior
    System.out.print("  +");
    for (int columna = 0; columna < numCols; columna++) {
        System.out.print("---+");
    }

    System.out.print("     "); // Espacio entre los dos tableros

    // Línea inferior del tablero para el tablero actual
    System.out.print("  +");
    for (int columna = 0; columna < numCols; columna++) {
        System.out.print("---+");
    }
    System.out.println(); // Cambio de línea
}



    public int leerEntero(String mensaje) {
        int valor = 0;
        boolean entradaValida = false;
        do {
            try {
                System.out.print(mensaje);
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

    public void cerrarScanner() {
        scanner.close();
    }
}