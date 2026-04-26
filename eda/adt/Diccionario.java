package eda.adt;

import eda.exceptions.ElementoDuplicado;
import eda.exceptions.ElementoNoEncontrado;

// Contrato base para los diccionarios (pares de clave-valor)
public interface Diccionario<C, V> {
    void insertar(C clave, V valor) throws ElementoDuplicado;

    V buscar(C clave) throws ElementoNoEncontrado;

    void eliminar(C clave) throws ElementoNoEncontrado;
}