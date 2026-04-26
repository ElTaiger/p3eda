package eda.exceptions;

// Para cuando intentamos sacar el máximo o mínimo y el árbol está pelado
public class NoHayElementos extends Exception {
    public NoHayElementos() {
        super("El árbol está vacío.");
    }
}