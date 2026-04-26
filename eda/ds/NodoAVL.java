package eda.ds;

// Nodo especial para el AVL, necesita guardar la altura para los cálculos de balanceo
public class NodoAVL<E> {
    E dato;
    int altura; // Guardamos la altura para saber cuándo rotar
    NodoAVL<E> izq, der;

    public NodoAVL(E dato) {
        this.dato = dato;
        this.altura = 1; // Todo nodo nuevo nace con altura 1 (como una hojita)
    }
}