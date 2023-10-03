package Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(PONER NUMERO)
public class Tablero {

    private Celda[][] elementos;
    private Celda[][] tableroAnterior;
    private ArrayList<Coordenada> coordenadasAleatorias = new ArrayList<>();
    private ArrayList<Coordenada> movimientosRealizados = new ArrayList<>();
    private ArrayList<Coordenada> caminoSolucion = new ArrayList<>();
    private int numFilas;
    private int numColumnas;
    private int nivel;
    private int pasosActuales;
    private boolean juegoGanado;
    private boolean juegoEnProgreso;
    private long tiempoInicio;

  
    
    public void configurarJuego(int filas, int columnas, int nivel) {
    this.numFilas = filas;
    this.numColumnas = columnas;
    this.nivel = nivel;
    this.pasosActuales = 0;
    this.juegoGanado = false;
    this.juegoEnProgreso = true;
    this.tiempoInicio = System.currentTimeMillis() / 1000;

    generarTableroAleatorio(filas, columnas, nivel); // No cambies el nivel aquí
}

    public boolean estaEnProgreso() {
        return juegoEnProgreso;
    }

    public void terminarJuego() {
        juegoEnProgreso = false;
    }

 public void realizarMovimiento(int fila, int columna) {
    copiarTableroActualATableroAnterior();
  
        if (fila == -2 && columna == -2) {
            // Realizar el último movimiento almacenado en coordenadasAleatorias
            if (!movimientosRealizados.isEmpty()) {
                Coordenada ultimoMovimiento = movimientosRealizados.remove(movimientosRealizados.size() - 1);
                fila = ultimoMovimiento.getFila()-1;
                columna = ultimoMovimiento.getColumna()-1;
            } else {
                // Si no hay movimientos almacenados, el juego continúa normalmente
                return;
            }
        }

        
        if (movimientoValido(fila, columna)) {
            // Actualizar el tablero después de cada movimiento
            cambiarColor(fila, columna);

            

            boolean gane = verificarVictoria();
            if (gane) {
                juegoGanado = true;
                terminarJuego();
            }
            pasosActuales++;
        }
    }



    public void almacenarMovimientoRealizado(int fila, int columna) {
        if (fila > 0 && columna > 0) {
            Coordenada movimientoRealizado = new Coordenada(fila, columna);
            movimientosRealizados.add(movimientoRealizado);
            if (!coordenadasAleatorias.isEmpty()) {
                Coordenada ultimaCoordenada = coordenadasAleatorias.get(coordenadasAleatorias.size() - 1);
                if (ultimaCoordenada.equals(movimientoRealizado)) {
                    // El movimiento coincide con la última coordenada en coordenadasAleatorias, elimínala y no lo agregues a movimientosRealizados.
                    coordenadasAleatorias.remove(coordenadasAleatorias.size() - 1);
                } else {
                    // El movimiento no coincide, agrégalo a movimientosRealizados y también en coordenadasAleatorias.
                    coordenadasAleatorias.add(movimientoRealizado);
                }
            }
        }
    

    

    
    }
    public String mostrarHistoriaMovimientos() {
        String historialMovimientos = "";
        for (Coordenada movimiento : movimientosRealizados) {
            historialMovimientos += ("(" + (movimiento.getFila()) + ", " + (movimiento.getColumna()) + ")" +" ");
        }
            return "Los movimientos Realizados son: " + historialMovimientos;
    }

    public String mostrarSecuenciaMovimientos() {
        String secuenciaSolucion = "";
        caminoSolucion.clear(); // Vaciar caminoSolucion
        caminoSolucion.addAll(coordenadasAleatorias);
        Collections.reverse(caminoSolucion);
        
        
        for (Coordenada pasoSolucion : caminoSolucion){
            secuenciaSolucion += ("(" + (pasoSolucion.getFila()) + ", " + (pasoSolucion.getColumna()) + ")" +" ");
        }
        
        return "Los movimientos para ganar son: " + secuenciaSolucion;
    }

    public String mostrarResultado() {
        long tiempoFin = System.currentTimeMillis() / 1000;
        long tiempoTotal = tiempoFin - tiempoInicio;

        String resultado = juegoGanado ? "Felicidades!! Has ganado el juego en "   + " y en " + (pasosActuales) + " pasos" + "\n"  : "Lo siento, no has ganado el juego.\n";
        resultado += "Tiempo total de la partida: " + tiempoTotal + " segundos";

        return resultado;
    }
    
    public Celda[][] getTableroAnterior() {
    return tableroAnterior;
}
    
    public void reiniciarTablero() {
        elementos = null;
}



    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public void setPasosActuales(int pasosActuales) {
        this.pasosActuales = pasosActuales;
    }

    public int getPasosActuales() {
        return pasosActuales;
    }

    public void setJuegoGanado(boolean juegoGanado) {
        this.juegoGanado = juegoGanado;
    }

    public boolean verificarVictoria() {
        // Inicialmente asumimos que el juego está ganado
        boolean juegoGanado = true;

        // Tomamos el primer color como referencia
        char primerColor = elementos[0][0].getColor();

        // Iterar sobre todas las celdas del tablero
        for (int fila = 0; fila < numFilas; fila++) {
            for (int columna = 0; columna < numColumnas; columna++) {
                // Obtener el color de la celda actual
                char colorActual = elementos[fila][columna].getColor();

                // Si el color de la celda actual es diferente al primer color, el juego no está ganado
                if (colorActual != primerColor) {
                    juegoGanado = false;
                    break; // Salir del bucle interno
                }
            }

            // Si el juego ya no está ganado, salir del bucle externo
            if (!juegoGanado) {
                break;
            }
        }

        // Devolver el estado final del juego
        return juegoGanado;
    }

    public Celda[][] getElementos() {
        return elementos;
    }

 
    public void generarTableroAleatorio(int filas, int columnas, int nivel) {
    Random random = new Random();
    elementos = new Celda[filas][columnas];
    
    // Llenar el tablero con elementos aleatorios y color 'R' (rojo)
    for (int fila = 0; fila < filas; fila++) {
        for (int columna = 0; columna < columnas; columna++) {
            char simbolo = obtenerElementoAleatorio(random);
            char color = 'R'; // Color fijo en 'R' (rojo)
            elementos[fila][columna] = new Celda(simbolo, color);
        }
    }

    // Generar coordenadas aleatorias para movimientos
    while (coordenadasAleatorias.size() < nivel) {
        Coordenada coordenada = obtenerCoordenadasAleatorias(coordenadasAleatorias);
        coordenadasAleatorias.add(coordenada);
    }

    // Realizar los movimientos aleatorios después de haber generado todas las coordenadas
    for (Coordenada coordenada : coordenadasAleatorias) {
        realizarMovimiento(coordenada.getFila()-1, coordenada.getColumna()-1);
        pasosActuales = 0;
    }
}

    public void copiarTableroActualATableroAnterior() {
    tableroAnterior = new Celda[numFilas][numColumnas];
    for (int fila = 0; fila < numFilas; fila++) {
        for (int columna = 0; columna < numColumnas; columna++) {
            tableroAnterior[fila][columna] = new Celda(elementos[fila][columna].getSimbolo(), elementos[fila][columna].getColor());
        }
    }
}

    
    public Coordenada obtenerCoordenadasAleatorias(ArrayList<Coordenada> coordenadasAleatorias) {
        Random random = new Random();
        Coordenada coordenada;
        boolean coordenadaUnica;

        do {
            int fila = random.nextInt(numFilas) + 1; // Genera un número entre 1 y numFilas inclusive
            int columna = random.nextInt(numColumnas) + 1; // Genera un número entre 1 y numColumnas inclusive
            coordenada = new Coordenada(fila, columna);

            // Verifica si la coordenada es única
            coordenadaUnica = true;
            for (Coordenada existente : coordenadasAleatorias) {
                if (existente.equals(coordenada)) {
                    coordenadaUnica = false;
                    break;
                }
            }
        } while (!coordenadaUnica);

        return coordenada;
    }



    private char obtenerElementoAleatorio(Random random) {
        // Array de elementos posibles (puedes personalizarlo según tus reglas)
        char[] elementosPosibles = { '/', '\\', '-', '|' };

        // Seleccionar un elemento aleatorio del array
        int indiceAleatorio = random.nextInt(elementosPosibles.length);
        return elementosPosibles[indiceAleatorio];
    }

   public void cambiarColor(int fila, int columna) {
    // Obtener la celda en la posición (fila, columna)
    Celda celda = elementos[fila][columna];

    // Cambiar el color de la celda actual alternando entre rojo ('R') y azul ('A')
    char nuevoColor = (celda.getColor() == 'R') ? 'A' : 'R';
    celda.setColor(nuevoColor);

    // Actualizar el color en las celdas relacionadas según las reglas del juego
    char simbolo = celda.getSimbolo();
    if (simbolo == '/') {
        cambiarColorDiagonalInversa(fila, columna, nuevoColor);
    } else if (simbolo == '\\') {
        cambiarColorDiagonal(fila, columna, nuevoColor);
    } else if (simbolo == '-') {
        cambiarColorFila(fila, columna, nuevoColor);
    } else if (simbolo == '|') {
        cambiarColorColumna(fila, columna, nuevoColor);
    }
}

private void cambiarColorDiagonal(int fila, int columna, char colorActual) {
    // Cambiar el color de la diagonal actual hacia arriba
    for (int i = fila, j = columna; i >= 0 && j >= 0; i--, j--) {
        cambiarColorCelda(i, j);
    }

    // Cambiar el color de la diagonal actual hacia abajo
    for (int i = fila, j = columna; i < numFilas && j < numColumnas; i++, j++) {
        cambiarColorCelda(i, j);
    }
}

private void cambiarColorDiagonalInversa(int fila, int columna, char colorActual) {
    // Cambiar el color de la diagonal inversa hacia arriba
    for (int i = fila, j = columna; i >= 0 && j < numColumnas; i--, j++) {
        cambiarColorCelda(i, j);
    }

    // Cambiar el color de la diagonal inversa hacia abajo
    for (int i = fila, j = columna; i < numFilas && j >= 0; i++, j--) {
        cambiarColorCelda(i, j);
    }
}

private void cambiarColorFila(int fila, int columna, char nuevoColor) {
    // Cambiar el color de la celda en la fila antes de iterar
    cambiarColorCelda(fila, columna); 
    
    // Cambiar todo el contenido de la fila al nuevo color
    for (int j = 0; j < numColumnas; j++) {
        cambiarColorCelda(fila, j);
    }
}

private void cambiarColorColumna(int fila, int columna, char nuevoColor) {
    // Cambiar el color de la celda en la columna antes de iterar
    cambiarColorCelda(fila, columna); 
    
    // Cambiar todo el contenido de la columna al nuevo color
    for (int i = 0; i < numFilas; i++) {
        cambiarColorCelda(i, columna); // Cambia el color de cada celda en la columna
    }
}




    private void cambiarColorCelda(int fila, int columna) {
        Celda celda = elementos[fila][columna];
        char nuevoColor = (celda.getColor() == 'R') ? 'A' : 'R';
        celda.setColor(nuevoColor);
    }


    public int getNumFilas() {
        return numFilas;
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public boolean movimientoValido(int fila, int columna) {
        boolean esValido = true;
        if (fila < 0 || fila >= numFilas || columna < 0 || columna >= numColumnas) {
            esValido = false; // Movimiento fuera de los límites del tablero
        }

       
        return esValido; // Si el movimiento es válido según las reglas
    }

  
    
   public void cargarDatosDesdeArchivo() {
    try {
        Scanner scanner = new Scanner(new File(".\\Test\\datos.txt"));

        // Leer el número de filas y columnas
        int filas = scanner.nextInt();
        int columnas = scanner.nextInt();
        scanner.nextLine(); // Consumir la línea en blanco después de los números
       
        
       
        configurarJuego(filas, columnas, 0);

        // Leer el tablero
        for (int fila = 0; fila < filas; fila++) {
            String filaTablero = scanner.nextLine();
            for (int columna = 0; columna < columnas; columna++) {
                char simbolo = filaTablero.charAt(columna*3); // Los símbolos están separados por un espacio
                char color = filaTablero.charAt(columna*3 + 1);
                // Configurar la celda en la fila y columna correspondiente
                getElementos()[fila][columna] = new Celda(simbolo, color);
            }
        }

        // Leer el nivel
        int nivel = scanner.nextInt();
        scanner.nextLine(); // Consumir la línea en blanco después del nivel

        
      

        // Leer los pasos para ganar
        while (scanner.hasNextInt()) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Coordenada coordenada = new Coordenada(x, y);
            coordenadasAleatorias.add(coordenada);
        }

        // Cerrar el scanner
        scanner.close();
    } catch (FileNotFoundException e) {
        System.err.println("El archivo 'datos.txt' no se encontro en la ubicacion especificada.");
    }
}

   
   public void configurarTableroPredefinido() {
    configurarJuego(5, 6, 0);

    //FALTAN LOS DATOS DE VERDAD DEL TABLERO
    // Configurar las celdas del tablero predefinido
  getElementos()[0][0] = new Celda('|', 'A');
    getElementos()[0][1] = new Celda('-', 'R');
    getElementos()[0][2] = new Celda('-', 'R');
    getElementos()[0][3] = new Celda('\\', 'A');
    getElementos()[0][4] = new Celda('\\', 'A');
    getElementos()[0][5] = new Celda('\\', 'A');

    getElementos()[1][0] = new Celda('|', 'A');
    getElementos()[1][1] = new Celda('/', 'R');
    getElementos()[1][2] = new Celda('-', 'R');
    getElementos()[1][3] = new Celda('-', 'A');
    getElementos()[1][4] = new Celda('/', 'A');
    getElementos()[1][5] = new Celda('\\', 'A');

    getElementos()[2][0] = new Celda('-', 'A');
    getElementos()[2][1] = new Celda('/', 'R');
    getElementos()[2][2] = new Celda('|', 'R');
    getElementos()[2][3] = new Celda('|', 'A');
    getElementos()[2][4] = new Celda('/', 'A');
    getElementos()[2][5] = new Celda('\\', 'A');

    getElementos()[3][0] = new Celda('|', 'A');
    getElementos()[3][1] = new Celda('-', 'R');
    getElementos()[3][2] = new Celda('-', 'R');
    getElementos()[3][3] = new Celda('\\', 'A');
    getElementos()[3][4] = new Celda('\\', 'A');
    getElementos()[3][5] = new Celda('\\', 'A');

    getElementos()[4][0] = new Celda('|', 'A');
    getElementos()[4][1] = new Celda('-', 'R');
    getElementos()[4][2] = new Celda('-', 'R');
    getElementos()[4][3] = new Celda('\\', 'A');
    getElementos()[4][4] = new Celda('\\', 'A');
    getElementos()[4][5] = new Celda('\\', 'A');
    
    

    // ACA TENEMOS QUE HACER LA LOGICA Y ALMACENAR LOS DATOS DE MOVIMIENTOS
}
   
   public void limpiarHistoriales(){
       coordenadasAleatorias.clear();
       movimientosRealizados.clear();
   }

    
}