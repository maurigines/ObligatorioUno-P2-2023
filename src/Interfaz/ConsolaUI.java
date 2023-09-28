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

        System.out.print("     "); // Espacio entre los dos tableros

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
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
            }
        } while (!entradaValida);
        return valor;
    }

    public String[] leerMovimiento(String mensaje) {
        String[] partes = null;
        boolean entradaValida = false;

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

   public static int obtenerFilaDesdeEntrada(String entrada) {
    int fila = 0;  // Valor predeterminado en caso de no encontrar una fila válida

    if ("-1 -1".equals(entrada)) {
        return -1;  // Si la entrada es "-1 -1", devuelve directamente -1
    }

    if (entrada.matches("\\d+ \\d+")) {
        String[] partes = entrada.split(" ");
        int filaStr = Integer.parseInt(partes[0]);
        int columnaStr = Integer.parseInt(partes[1]);

        fila = filaStr;
    } else if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
        char letra = entrada.charAt(0);
        if (letra >= 'a' && letra <= 'z') {
            fila = letra - 'a' + 'A';  // Convierte a mayúscula
        } else if (letra >= 'A' && letra <= 'Z') {
            fila = letra;
        }
    }

    return fila;
}

public static int obtenerColumnaDesdeEntrada(String entrada) {
    int columna = 0;  // Valor predeterminado en caso de no encontrar una columna válida

    if ("-1 -1".equals(entrada)) {
        return -1;  // Si la entrada es "-1 -1", devuelve directamente -1
    }

    if (entrada.matches("\\d+ \\d+")) {
        String[] partes = entrada.split(" ");
        int filaStr = Integer.parseInt(partes[0]);
        int columnaStr = Integer.parseInt(partes[1]);

        columna = columnaStr;
    } else if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
        char letra = entrada.charAt(0);
        if (letra >= 'a' && letra <= 'z') {
            columna = letra - 'a' + 'A';  // Convierte a mayúscula
        } else if (letra >= 'A' && letra <= 'Z') {
            columna = letra;
        }
    }

    return columna;
}

public static String obtenerOpcionDesdeEntrada(String entrada) {
    // Obtén el último carácter de la entrada como opción
    if (entrada.length() == 1) {
        char opcion = entrada.charAt(0);
        if (opcion >= 'a' && opcion <= 'z') {
            return Character.toString(opcion).toUpperCase();  // Convierte a mayúscula
        } else if (opcion >= 'A' && opcion <= 'Z') {
            return Character.toString(opcion);
        }
    }

    // Si no se pudo encontrar una opción válida, devuelve una cadena vacía
    return "";
}


    public void cerrarScanner() {
        scanner.close();
    }
}