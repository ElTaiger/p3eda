package eda.ds;

import eda.exceptions.ElementoDuplicado;
import eda.exceptions.ElementoNoEncontrado;
import eda.exceptions.NoHayElementos;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class ABB<E extends Comparable<E>> {
    NodoABB<E> raiz = null;

    public ABB() {
    }

    // Función para buscar un elemento
    public NodoABB<E> buscar(E x) throws ElementoNoEncontrado {
        NodoABB<E> res = buscar(x, raiz);
        if (res == null)
            throw new ElementoNoEncontrado();
        return res;
    }

    // El método recursivo que hace el curro de verdad
    private NodoABB<E> buscar(E x, NodoABB<E> n) {
        if (n == null)
            return null;
        int compare = n.dato.compareTo(x);
        // Si es mayor, tiramos por la derecha, si no por la izquierda
        if (compare < 0)
            return buscar(x, n.der);
        else if (compare > 0)
            return buscar(x, n.izq);
        else
            return n; // ¡Lo pillamos!
    }

    // Función para meter cosas nuevas
    public void insertar(E x) throws ElementoDuplicado {
        raiz = insertarSinDuplicados(x, raiz);
    }

    private NodoABB<E> insertarSinDuplicados(E x, NodoABB<E> n) throws ElementoDuplicado {
        if (n == null)
            return new NodoABB<E>(x);

        int compare = n.dato.compareTo(x);
        // Comprobamos que no esté repetido porque no dejamos meter dos iguales
        if (compare == 0)
            throw new ElementoDuplicado();
        else if (compare < 0)
            n.der = insertarSinDuplicados(x, n.der);
        else
            n.izq = insertarSinDuplicados(x, n.izq);

        return n;
    }

    // --- RECORRIDOS (Para pasearnos por el árbol) ---

    public void preorden() {
        preorden(raiz);
        System.out.println();
    }

    private void preorden(NodoABB<E> n) {
        if (n != null) {
            System.out.print(n.dato + " "); // Primero el nodo actual
            preorden(n.izq); // Luego todo lo de la izq
            preorden(n.der); // Y por último lo de la der
        }
    }

    public void inorden() {
        inorden(raiz);
        System.out.println();
    }

    private void inorden(NodoABB<E> n) {
        if (n != null) {
            inorden(n.izq);
            System.out.print(n.dato + " "); // Al hacerlo en inorden salen ordenados
            inorden(n.der);
        }
    }

    public void postorden() {
        postorden(raiz);
        System.out.println();
    }

    private void postorden(NodoABB<E> n) {
        if (n != null) {
            postorden(n.izq);
            postorden(n.der);
            System.out.print(n.dato + " ");
        }
    }

    // Recorrido por niveles
    public void anchura() {
        if (raiz == null)
            return;
        Queue<NodoABB<E>> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            // Sacamos el primero de la cola y metemos a sus hijos para luego
            NodoABB<E> n = cola.poll();
            System.out.print(n.dato + " ");
            if (n.izq != null)
                cola.add(n.izq);
            if (n.der != null)
                cola.add(n.der);
        }
        System.out.println();
    }

    // Función que nos piden para sacar los mayores a un número
    public List<E> mayoresQue(E elemento) {
        List<E> resultado = new ArrayList<>();
        mayoresQue(raiz, elemento, resultado);
        return resultado;
    }

    private void mayoresQue(NodoABB<E> n, E elemento, List<E> resultado) {
        if (n == null)
            return;

        int compare = n.dato.compareTo(elemento);

        // Si el actual es mayor, lo guardamos y miramos a ambos lados
        if (compare > 0) {
            mayoresQue(n.izq, elemento, resultado);
            resultado.add(n.dato);
            mayoresQue(n.der, elemento, resultado);
        } else {
            // Si es menor o igual, pasamos olímpicamente de la izquierda
            mayoresQue(n.der, elemento, resultado);
        }
    }

    // --- SACAR MÁXIMO, MÍNIMO Y BORRAR ---

    public E maximo() throws NoHayElementos {
        if (raiz == null)
            throw new NoHayElementos();
        return maximo(raiz);
    }

    // El máximo siempre es el que está más a la derecha del todo
    private E maximo(NodoABB<E> n) {
        NodoABB<E> actual = n;
        while (actual.der != null)
            actual = actual.der;
        return actual.dato;
    }

    public E minimo() throws NoHayElementos {
        if (raiz == null)
            throw new NoHayElementos();
        return minimo(raiz);
    }

    // El mínimo siempre es el que está más a la izquierda
    private E minimo(NodoABB<E> n) {
        NodoABB<E> actual = n;
        while (actual.izq != null)
            actual = actual.izq;
        return actual.dato;
    }

    public void eliminarMin() {
        raiz = eliminarMin(raiz);
    }

    public NodoABB<E> eliminarMin(NodoABB<E> n) {
        if (n.izq == null)
            return n.der;
        else {
            n.izq = eliminarMin(n.izq);
            return n;
        }
    }

    public void eliminar(E x) throws ElementoNoEncontrado {
        raiz = eliminar(x, raiz);
    }

    private NodoABB<E> eliminar(E x, NodoABB<E> n) throws ElementoNoEncontrado {
        if (n == null)
            throw new ElementoNoEncontrado();
        int compare = n.dato.compareTo(x);

        if (compare < 0)
            n.der = eliminar(x, n.der);
        else if (compare > 0)
            n.izq = eliminar(x, n.izq);
        else if (n.izq != null && n.der != null) {
            // ¡¡AQUI ESTABA EL ERROR DEL TRY-CATCH!!
            // Si tiene dos hijos, pillamos el más pequeño del lado derecho para sustituirlo
            n.dato = minimo(n.der);
            n.der = eliminarMin(n.der);
        } else {
            // Si solo tiene un hijo o ninguno, puenteamos el nodo y listo
            n = (n.izq != null) ? n.izq : n.der;
        }
        return n;
    }
}