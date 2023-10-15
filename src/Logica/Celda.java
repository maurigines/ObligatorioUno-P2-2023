// Autores: Mauricio Gines Martinez Miglionico (255043), Andres Sarmiento(256909)

package Logica;


public class Celda {
    private char simbolo;
    private char color;

    public Celda(char simbolo, char color) {
        this.simbolo = simbolo;
        this.color = color;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }
}
