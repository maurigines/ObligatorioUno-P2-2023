// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(256909)
package Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class Tablero {

    private Celda[][] elementos; 
    private Celda[][] tableroAnterior;
    private ArrayList<Coordenada> coordenadasAleatorias = new ArrayList<>();
    private ArrayList<Coordenada> movimientosRealizados = new ArrayList<>();
    private ArrayList<Coordenada> caminoSolucion = new ArrayList<>();
    private int numFilas = 0;
    private int numColumnas = 0;
    private int nivel = 0;
    private int pasosActuales = 0;
    private boolean juegoGanado = false;
    private boolean juegoEnProgreso = false;
    private long tiempoInicio = 0;

  
    
    public void configurarJuego(int filas, int columnas, int nivel) {
    this.numFilas = filas;
    this.numColumnas = columnas;
    this.nivel = nivel;
    this.pasosActuales = 0;
    this.juegoGanado = false;
    this.juegoEnProgreso = true;
    this.tiempoInicio = System.currentTimeMillis() / 1000;
    
   
    do {
        generarTableroAleatorio(filas, columnas, nivel);
        this.pasosActuales = 0;
        this.tiempoInicio = System.currentTimeMillis() / 1000;
    } while (juegoGanado);
    
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
           
            if (!movimientosRealizados.isEmpty()) {
                Coordenada ultimoMovimientoRealizado = movimientosRealizados.get(movimientosRealizados.size() - 1);
                fila = ultimoMovimientoRealizado.getFila() - 1;
                columna = ultimoMovimientoRealizado.getColumna() - 1;
               
             
                    

                boolean encontrado = false;
                for (Coordenada coordenada : coordenadasAleatorias) {
                    if (ultimoMovimientoRealizado.equals(coordenada)) {
                        encontrado = true;
                        System.out.println("Encontrado");
                        break;
                    }
                }

                if (encontrado) {
                    movimientosRealizados.remove(movimientosRealizados.size() - 1);
                    coordenadasAleatorias.remove(ultimoMovimientoRealizado);
                } else {
                    coordenadasAleatorias.add(ultimoMovimientoRealizado);
                    movimientosRealizados.remove(movimientosRealizados.size() - 1);
                }
            }
        }




        
        if (movimientoValido(fila, columna)) {
           
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
            boolean encontrado = false;
            int indexEncontrado = -1;
            for (int i = 0; i < coordenadasAleatorias.size(); i++) {
                if (coordenadasAleatorias.get(i).equals(movimientoRealizado) && !encontrado) {
                    encontrado = true;
                    indexEncontrado = i;
                    
                }
            }
            if (encontrado) {
                coordenadasAleatorias.remove(indexEncontrado);
            } else {
                coordenadasAleatorias.add(movimientoRealizado);
            }
        }
    }


    

    

    
    }
    public ArrayList <Coordenada> mostrarHistoriaMovimientos() {
            return movimientosRealizados;
    }

    public  ArrayList <Coordenada> mostrarSecuenciaMovimientos() {
        caminoSolucion.clear(); 
        caminoSolucion.addAll(coordenadasAleatorias);
        Collections.reverse(caminoSolucion);
              
                
        return caminoSolucion;
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
       
        boolean juegoGanado = true;

       
        char primerColor = elementos[0][0].getColor();

        
        for (int fila = 0; fila < numFilas; fila++) {
            for (int columna = 0; columna < numColumnas; columna++) {
               
                char colorActual = elementos[fila][columna].getColor();

               
                if (colorActual != primerColor) {
                    juegoGanado = false;
                    break; 
                }
            }

           
            if (!juegoGanado) {
                break;
            }
        }

       
        return juegoGanado;
    }

    public Celda[][] getElementos() {
        return elementos;
    }

 
    public void generarTableroAleatorio(int filas, int columnas, int nivel) {
    Random random = new Random();
    elementos = new Celda[filas][columnas];
    
    
    for (int fila = 0; fila < filas; fila++) {
        for (int columna = 0; columna < columnas; columna++) {
            char simbolo = obtenerElementoAleatorio(random);
            char color = 'R'; // Color fijo en 'R' (rojo)
            elementos[fila][columna] = new Celda(simbolo, color);
        }
    }

   
    while (coordenadasAleatorias.size() < nivel) {
        Coordenada coordenada = obtenerCoordenadasAleatorias(coordenadasAleatorias);
        coordenadasAleatorias.add(coordenada);
    }

   
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
            int fila = random.nextInt(numFilas) + 1; 
            int columna = random.nextInt(numColumnas) + 1; 
            coordenada = new Coordenada(fila, columna);

            
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
       
        char[] elementosPosibles = { '/', '\\', '-', '|' };

      
        int indiceAleatorio = random.nextInt(elementosPosibles.length);
        return elementosPosibles[indiceAleatorio];
    }

   public void cambiarColor(int fila, int columna) {
    
    Celda celda = elementos[fila][columna];

   
    char nuevoColor = (celda.getColor() == 'R') ? 'A' : 'R';
    celda.setColor(nuevoColor);

    
    char simbolo = celda.getSimbolo();
        switch (simbolo) {
            case '/' -> cambiarColorDiagonalInversa(fila, columna, nuevoColor);
            case '\\' -> cambiarColorDiagonal(fila, columna, nuevoColor);
            case '-' -> cambiarColorFila(fila, columna, nuevoColor);
            case '|' -> cambiarColorColumna(fila, columna, nuevoColor);
            default -> {
            }
        }
}

private void cambiarColorDiagonal(int fila, int columna, char colorActual) {
   
    for (int i = fila, j = columna; i >= 0 && j >= 0; i--, j--) {
        cambiarColorCelda(i, j);
    }

    
    for (int i = fila, j = columna; i < numFilas && j < numColumnas; i++, j++) {
        cambiarColorCelda(i, j);
    }
}

private void cambiarColorDiagonalInversa(int fila, int columna, char colorActual) {
   
    for (int i = fila, j = columna; i >= 0 && j < numColumnas; i--, j++) {
        cambiarColorCelda(i, j);
    }

   
    for (int i = fila, j = columna; i < numFilas && j >= 0; i++, j--) {
        cambiarColorCelda(i, j);
    }
}

private void cambiarColorFila(int fila, int columna, char nuevoColor) {
   
    cambiarColorCelda(fila, columna); 
    
    
    for (int j = 0; j < numColumnas; j++) {
        cambiarColorCelda(fila, j);
    }
}

private void cambiarColorColumna(int fila, int columna, char nuevoColor) {
    
    cambiarColorCelda(fila, columna); 
    
   
    for (int i = 0; i < numFilas; i++) {
        cambiarColorCelda(i, columna); 
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
            esValido = false; 
        }

       
        return esValido; 
    }

  
    
   public void cargarDatosDesdeArchivo() {
    try {
        Scanner scanner = new Scanner(new File(".\\Test\\datos.txt"));

       
        int filas = scanner.nextInt();
        int columnas = scanner.nextInt();
        scanner.nextLine(); 
       
        
       
        configurarJuego(filas, columnas, 0);

       
        for (int fila = 0; fila < filas; fila++) {
            String filaTablero = scanner.nextLine();
            for (int columna = 0; columna < columnas; columna++) {
                char simbolo = filaTablero.charAt(columna*3); 
                char color = filaTablero.charAt(columna*3 + 1);
                
                getElementos()[fila][columna] = new Celda(simbolo, color);
            }
        }

        
        int nivel = scanner.nextInt();
        scanner.nextLine(); 

        
      
        for (int i = 0; i < nivel; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Coordenada coordenada = new Coordenada(x, y);
            coordenadasAleatorias.add(coordenada);
        }
     
        scanner.close();
    } catch (FileNotFoundException e) {
        System.err.println("El archivo 'datos.txt' no se encontro en la ubicacion especificada.");
    }
}

   
   public void configurarTableroPredefinido() {
    configurarJuego(5, 6, 0);

    
   
    getElementos()[0][0] = new Celda('|', 'A');
    getElementos()[0][1] = new Celda('|', 'A');
    getElementos()[0][2] = new Celda('-', 'R');
    getElementos()[0][3] = new Celda('/', 'A');
    getElementos()[0][4] = new Celda('|', 'R');
    getElementos()[0][5] = new Celda('-', 'R');

    getElementos()[1][0] = new Celda('-', 'R');
    getElementos()[1][1] = new Celda('/', 'A');
    getElementos()[1][2] = new Celda('|', 'A');
    getElementos()[1][3] = new Celda('-', 'A');
    getElementos()[1][4] = new Celda('-', 'R');
    getElementos()[1][5] = new Celda('-', 'R');

    getElementos()[2][0] = new Celda('-', 'R');
    getElementos()[2][1] = new Celda('-', 'R');
    getElementos()[2][2] = new Celda('|', 'A');
    getElementos()[2][3] = new Celda('-', 'R');
    getElementos()[2][4] = new Celda('/', 'R');
    getElementos()[2][5] = new Celda('-', 'R');

    getElementos()[3][0] = new Celda('\\', 'R');
    getElementos()[3][1] = new Celda('-', 'R');
    getElementos()[3][2] = new Celda('|', 'R');
    getElementos()[3][3] = new Celda('\\', 'R');
    getElementos()[3][4] = new Celda('|', 'A');
    getElementos()[3][5] = new Celda('|', 'R');

    getElementos()[4][0] = new Celda('\\', 'R');
    getElementos()[4][1] = new Celda('/', 'R');
    getElementos()[4][2] = new Celda('/', 'R');
    getElementos()[4][3] = new Celda('|', 'A');
    getElementos()[4][4] = new Celda('/', 'A');
    getElementos()[4][5] = new Celda('\\', 'A');
    
   
    Coordenada coordenadaA = new Coordenada(4, 4);
    Coordenada coordenadaB = new Coordenada(5, 6);
    Coordenada coordenadaC = new Coordenada(5, 4);
    
    

   
    Coordenada coordenada1 = new Coordenada(4, 4);
    Coordenada coordenada2 = new Coordenada(5, 6);
    Coordenada coordenada3 = new Coordenada(5, 4);

    coordenadasAleatorias.add(coordenada1);
    coordenadasAleatorias.add(coordenada2);
    coordenadasAleatorias.add(coordenada3);
}
   
   public void limpiarHistoriales(){
       coordenadasAleatorias.clear();
       movimientosRealizados.clear();
   }

    public long getTiempoInicio() {
        return tiempoInicio;
    }

    public boolean isJuegoGanado() {
        return juegoGanado;
    }

    
}