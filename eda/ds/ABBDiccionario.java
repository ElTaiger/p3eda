package eda.ds;

import eda.adt.Diccionario;
import eda.exceptions.ElementoDuplicado;
import eda.exceptions.ElementoNoEncontrado;

// Aquí montamos el Diccionario usando nuestro propio ABB por debajo
public class ABBDiccionario<C extends Comparable<C>, V> implements Diccionario<C, V> {
    private ABB<EntradaDic<C, V>> abb = new ABB<EntradaDic<C, V>>();

    public void insertar(C c, V v) throws ElementoDuplicado {
        abb.insertar(new EntradaDic<C, V>(c, v));
    }

    public V buscar(C c) throws ElementoNoEncontrado {
        // Devolvemos solo el valor, no la caja entera
        return abb.buscar(new EntradaDic<C, V>(c)).dato.valor;
    }

    public void eliminar(C c) throws ElementoNoEncontrado {
        abb.eliminar(new EntradaDic<C, V>(c));
    }
}