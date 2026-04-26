package eda.ds;

import eda.adt.ColaPrioridad;
import eda.exceptions.ElementoDuplicado;
import eda.exceptions.NoHayElementos;
import eda.exceptions.ElementoNoEncontrado;

// Usamos el ABB para montar una Cola de Prioridad rapidita
public class ABBColaPrioridad<E extends Comparable<E>> implements ColaPrioridad<E> {
    private ABB<E> abb = new ABB<>();

    public void insertar(E x) {
        try {
            abb.insertar(x);
        } catch (ElementoDuplicado e) {
            // Si ya está repetido, pasamos de meterlo otra vez
        }
    }

    // Devolvemos lo que saque el ABB al buscar el máximo
    public E maximo() {
        try {
            return abb.maximo();
        } catch (NoHayElementos e) {
            return null;
        }
    }

    // Sacamos el máximo y de paso lo borramos del árbol
    public E eliminarMax() {
        try {
            E max = abb.maximo();
            abb.eliminar(max);
            return max;
        } catch (NoHayElementos | ElementoNoEncontrado e) {
            return null;
        }
    }
}