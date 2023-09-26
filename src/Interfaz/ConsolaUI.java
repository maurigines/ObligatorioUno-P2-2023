package Interfaz;

import Logica.Celda;
import java.util.Scanner;

// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(PONER NUMERO)

public class ConsolaUI {

    private Scanner scanner;

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



    
    public int leerEntero(String mensaje) {
        int valor = 0;
        boolean entradaValida = false;
        do {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine());
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
            }
        } while (!entradaValida);
        return valor;
    }
    
    public String[] leerMovimiento(String mensaje) {
    int valor = 0;
    boolean entradaValida = false;
    String[] partes = null;
    do {
        try {
            mostrarMensaje(mensaje);
            String entrada = scanner.nextLine();
            partes = entrada.split(" ");
            if (partes.length != 2) {
                mostrarMensaje("Entrada no válida. Debe ingresar filas y columnas separadas por un espacio.");
                continue; // Volver a solicitar la entrada
            }

            // Validar que ambas partes sean números enteros (positivos o negativos)
            int fila = Integer.parseInt(partes[0]);
            int columna = Integer.parseInt(partes[1]);
            
            entradaValida = true;
        } catch (NumberFormatException e) {
            mostrarMensaje("Entrada no válida. Debe ingresar dos números enteros.");
        }
    } while (!entradaValida);
    return partes;
}

    public String leerCadena() {
        System.out.print("Ingrese una cadena: ");
        return scanner.nextLine();
    }
    
    

    


    public void cerrarScanner() {
        scanner.close();
    }
}
