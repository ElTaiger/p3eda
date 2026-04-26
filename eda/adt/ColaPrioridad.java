package eda.adt;

// Contrato base para la cola de prioridad
public interface ColaPrioridad<E extends Comparable<E>> {
    public void insertar(E x);

    public E maximo();

    public E eliminarMax();
}