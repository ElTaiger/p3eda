package eda.exceptions;

// Salta si intentamos meter un dato que ya estaba metido antes
public class ElementoDuplicado extends Exception {
    public ElementoDuplicado() {
        super("El elemento ya existe en el árbol.");
    }
}