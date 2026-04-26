package eda.ds;

// Nodo para el árbol 2-3, el que puede tener 1 o 2 valores y hasta 3 ramas
public class Nodo23<E extends Comparable<E>> {
    E valorIzq, valorDer; // Puede guardar hasta dos datos a la vez
    Nodo23<E> hijoIzq, hijoMedio, hijoDer;

    public Nodo23(E valorIzq) {
        this.valorIzq = valorIzq;
        this.valorDer = null;
        this.hijoIzq = this.hijoMedio = this.hijoDer = null;
    }

    // Función rápida para comprobar si el nodo está a tope (con 2 datos)
    public boolean esTresNodo() {
        return valorDer != null;
    }

    // Para saber si hemos llegado al final y no tiene hijos
    public boolean esHoja() {
        return hijoIzq == null && hijoMedio == null && hijoDer == null;
    }
}