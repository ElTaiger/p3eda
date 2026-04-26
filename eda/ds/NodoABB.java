package eda.ds;

// La cajita básica para montar el Árbol Binario de Búsqueda
public class NodoABB<E> {
    E dato;
    NodoABB<E> izq, der; // Los dos hijos: el menor a la izq y mayor a la der

    public NodoABB(E dato) {
        this.dato = dato;
    }

    public NodoABB(NodoABB<E> izq, NodoABB<E> der) {
        this.izq = izq;
        this.der = der;
    }
}