package eda.exceptions;

// Excepción para cuando buscamos algo y no está en el árbol
public class ElementoNoEncontrado extends Exception {
    public ElementoNoEncontrado() {
        super("El elemento buscado no se encuentra en el árbol.");
    }
}