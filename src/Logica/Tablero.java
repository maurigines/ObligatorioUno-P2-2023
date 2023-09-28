package Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(PONER NUMERO)
public class Tablero {

    private Celda[][] elementos;
    private ArrayList<Coordenada> coordenadasAleatorias = new ArrayList<>();
   // private ArrayList<Coordenada> movimientosRealizados = new ArrayList<>();
    private Stack<Coordenada> movimientosRealizados = new Stack<>();
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
    if (fila == -1 && columna == -1) {
        // Deshacer el último movimiento almacenado en movimientosRealizados
        if (!movimientosRealizados.isEmpty()) {
            Coordenada ultimoMovimiento = movimientosRealizados.remove(movimientosRealizados.size() - 1);
            fila = ultimoMovimiento.getFila();
            columna = ultimoMovimiento.getColumna();
            deshacerMovimiento(fila, columna); 
        } else {
            // Si no hay movimientos almacenados, el juego continúa normalmente
            return;
        }
    }

    if (movimientoValido(fila, columna)) {
        // Actualizar el tablero después de cada movimiento
        cambiarColor(fila, columna);

        Coordenada movimientoRealizado = new Coordenada(fila, columna);
        movimientosRealizados.push(movimientoRealizado);

        // Agregar aquí la lógica para controlar los movimientos si es necesario

        boolean gane = verificarVictoria();
        if (gane) {
            juegoGanado = true;
            terminarJuego();
        }
        pasosActuales++;
    }
}










    public String mostrarResultado() {
        long tiempoFin = System.currentTimeMillis() / 1000;
        long tiempoTotal = tiempoFin - tiempoInicio;

        String resultado = juegoGanado ? "Felicidades!! Has ganado el juego en "   + " y en " + (pasosActuales-nivel) + " pasos" + "\n"  : "Lo siento, no has ganado el juego.\n";
        resultado += "Tiempo total de la partida: " + tiempoTotal + " segundos";

        return resultado;
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
    for (int i = 0; i < nivel; i++) {
        Coordenada coordenada = obtenerCoordenadasAleatorias();
        coordenadasAleatorias.add(coordenada);
    }

    // Realizar los movimientos aleatorios después de haber generado todas las coordenadas
    for (Coordenada coordenada : coordenadasAleatorias) {
        realizarMovimiento(coordenada.getFila(), coordenada.getColumna());
    }
}

    
   public Coordenada obtenerCoordenadasAleatorias() {
       
       //HAY QUE CONTROLAR QUE NO SE GENEREN DOS VECES LA MISMA COORDENADA.
    Random random = new Random();
    int fila = random.nextInt(numFilas); // Genera un número aleatorio entre 0 y numFilas-1
    int columna = random.nextInt(numColumnas); // Genera un número aleatorio entre 0 y numColumnas-1
    return new Coordenada(fila, columna);
}



public String obtenerPasosNecesariosParaGanar() {
    
    StringBuilder sb = new StringBuilder("Los pasos necesarios para ganar son: ");
    
    //ESTO HAY QUE HACERLO BIEN
    for (int i = 0; i < coordenadasAleatorias.size(); i++) {
        Coordenada coordenada = coordenadasAleatorias.get(i);
        int fila = coordenada.getFila() + 1; // Incrementa en 1
        int columna = coordenada.getColumna() + 1; // Incrementa en 1
        
        // Agregar las coordenadas al StringBuilder en el formato (fila, columna)
        sb.append("(").append(fila).append(", ").append(columna).append(")");
        
        // Agregar una coma y un espacio si no es la última coordenada
        if (i < coordenadasAleatorias.size() - 1) {
            sb.append(", ");
        }
    }
    
    return sb.toString();
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
            esValido = false; // Movimiento fuera de los límites del tablero o  va hacia atras
        }

       
        return esValido; // Si el movimiento es válido según las reglas
    }
    
    
private void deshacerMovimiento(int fila, int columna) {
    if (!movimientosRealizados.isEmpty()) {
        // Acá saco la última coordenada realizada
        Coordenada ultimoMovimiento = movimientosRealizados.pop();
        fila = ultimoMovimiento.getFila();
        columna = ultimoMovimiento.getColumna();    
        
        //sacar la celda en al pos (fil.col)
        Celda celda = elementos[fila][columna];
        
        // Revertir el cambio de color de la celda
        char nuevoColor = (celda.getColor() == 'R') ? 'A' : 'R';
        celda.setColor(nuevoColor);

        // Actualizar el color en las celdas relacionadas si es necesario
        char simbolo = celda.getSimbolo();
        if (simbolo == '/') {
            // Revertir cambios relacionados con el símbolo '/'
            // Por ejemplo, cambiar el color de las celdas en las diagonales
            cambiarColorDiagonalInversa(fila, columna, nuevoColor);
            cambiarColorDiagonal(fila, columna, nuevoColor);
        } else if (simbolo == '\\') {
            // Revertir cambios relacionados con el símbolo '\'
            // Por ejemplo, cambiar el color de las celdas en las diagonales inversas
            cambiarColorDiagonal(fila, columna, nuevoColor);
            cambiarColorDiagonalInversa(fila, columna, nuevoColor);
        } else if (simbolo == '-') {
            // Revertir cambios relacionados con el símbolo '-'
            // Por ejemplo, cambiar el color de las celdas en la misma fila
            cambiarColorFila(fila, columna, nuevoColor);
        } else if (simbolo == '|') {
            // Revertir cambios relacionados con el símbolo '|'
            // Por ejemplo, cambiar el color de las celdas en la misma columna
            cambiarColorColumna(fila, columna, nuevoColor);
        }
    } else{
        System.out.println("No hay movimientos para deshacer.");
    }
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
            //ACA ESTO HAY QUE ALMACENAR
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



    
}

