package eda.ds;

import java.util.LinkedList;
import java.util.Queue;

public class Arbol23<E extends Comparable<E>> {

    private Nodo23<E> raiz;

    public Arbol23() {
        this.raiz = null;
    }

    // Búsqueda normal y corriente
    public boolean buscar(E dato) {
        return buscar(raiz, dato);
    }

    private boolean buscar(Nodo23<E> n, E dato) {
        if (n == null)
            return false;

        // Miramos si lo tenemos en el propio nodo
        if (dato.equals(n.valorIzq))
            return true;
        if (n.esTresNodo() && dato.equals(n.valorDer))
            return true;

        if (n.esHoja())
            return false; // Hemos llegado al final y no está

        // Elegimos por qué camino bajar según el valor
        if (dato.compareTo(n.valorIzq) < 0) {
            return buscar(n.hijoIzq, dato);
        } else if (!n.esTresNodo() || dato.compareTo(n.valorDer) < 0) {
            return buscar(n.hijoMedio, dato);
        } else {
            return buscar(n.hijoDer, dato);
        }
    }

    // Truco: una clase privada para gestionar cuando partimos un nodo que está
    // lleno
    private class ResultadoSplit {
        E valorPromovido;
        Nodo23<E> nuevoNodoDer;

        ResultadoSplit(E valor, Nodo23<E> nodo) {
            this.valorPromovido = valor;
            this.nuevoNodoDer = nodo;
        }
    }

    public void insertar(E dato) {
        if (raiz == null) {
            raiz = new Nodo23<>(dato);
            return;
        }

        // Si al meter algo la raíz acaba rompiéndose, toca crear una raíz nueva por
        // encima
        ResultadoSplit res = insertarRec(raiz, dato);
        if (res != null) {
            Nodo23<E> nuevaRaiz = new Nodo23<>(res.valorPromovido);
            nuevaRaiz.hijoIzq = raiz;
            nuevaRaiz.hijoMedio = res.nuevoNodoDer;
            raiz = nuevaRaiz;
        }
    }

    private ResultadoSplit insertarRec(Nodo23<E> n, E dato) {
        // Pasamos de meter repetidos
        if (dato.equals(n.valorIzq) || (n.esTresNodo() && dato.equals(n.valorDer))) {
            return null;
        }

        // Si ya estamos abajo del todo en una hoja, probamos a meterlo aquí
        if (n.esHoja()) {
            return insertarEnNodo(n, dato, null);
        }

        ResultadoSplit res;
        // Toca bajar a buscar hueco
        if (dato.compareTo(n.valorIzq) < 0) {
            res = insertarRec(n.hijoIzq, dato);
        } else if (!n.esTresNodo() || dato.compareTo(n.valorDer) < 0) {
            res = insertarRec(n.hijoMedio, dato);
        } else {
            res = insertarRec(n.hijoDer, dato);
        }

        if (res == null)
            return null; // Si no hubo movida al insertar abajo, terminamos

        // Si el hijo reventó al insertarle, subimos el valor al nodo actual
        return insertarEnNodo(n, res.valorPromovido, res.nuevoNodoDer);
    }

    private ResultadoSplit insertarEnNodo(Nodo23<E> n, E dato, Nodo23<E> nuevoHijo) {
        // Si hay hueco en este nodo (solo tenía 1 valor), lo metemos y listo
        if (!n.esTresNodo()) {
            if (dato.compareTo(n.valorIzq) < 0) {
                n.valorDer = n.valorIzq;
                n.valorIzq = dato;
                n.hijoDer = n.hijoMedio;
                n.hijoMedio = nuevoHijo;
            } else {
                n.valorDer = dato;
                n.hijoDer = nuevoHijo;
            }
            return null;
        }

        // Si ya tenía 2 valores, toca romper el nodo en dos pedazos
        E valorPromovido;
        Nodo23<E> nuevoNodo = new Nodo23<>(null);

        if (dato.compareTo(n.valorIzq) < 0) {
            // El nuevo es el más pequeño. El del medio (el izquierdo de antes) va pa'rriba
            valorPromovido = n.valorIzq;
            nuevoNodo.valorIzq = n.valorDer;
            nuevoNodo.hijoIzq = n.hijoMedio;
            nuevoNodo.hijoMedio = n.hijoDer;

            n.valorIzq = dato;
            n.valorDer = null;
            n.hijoMedio = nuevoHijo;
            n.hijoDer = null;

        } else if (dato.compareTo(n.valorDer) < 0) {
            // El nuevo cae justo en medio. Ese es el que sube
            valorPromovido = dato;
            nuevoNodo.valorIzq = n.valorDer;
            nuevoNodo.hijoIzq = nuevoHijo;
            nuevoNodo.hijoMedio = n.hijoDer;

            n.valorDer = null;
            n.hijoDer = null;

        } else {
            // El nuevo es el más grande. Sube el que era el derecho de antes
            valorPromovido = n.valorDer;
            nuevoNodo.valorIzq = dato;
            nuevoNodo.hijoIzq = n.hijoDer;
            nuevoNodo.hijoMedio = nuevoHijo;

            n.valorDer = null;
            n.hijoDer = null;
        }

        return new ResultadoSplit(valorPromovido, nuevoNodo);
    }

    // --- RECORRIDOS PARA EL ÁRBOL 2-3 ---
    public void preorden() {
        preorden(raiz);
        System.out.println();
    }

    private void preorden(Nodo23<E> n) {
        if (n != null) {
            System.out.print(n.valorIzq + " ");
            if (n.esTresNodo())
                System.out.print(n.valorDer + " ");
            preorden(n.hijoIzq);
            preorden(n.hijoMedio);
            preorden(n.hijoDer);
        }
    }

    public void inorden() {
        inorden(raiz);
        System.out.println();
    }

    private void inorden(Nodo23<E> n) {
        if (n != null) {
            inorden(n.hijoIzq);
            System.out.print(n.valorIzq + " ");
            inorden(n.hijoMedio);
            if (n.esTresNodo()) {
                System.out.print(n.valorDer + " ");
                inorden(n.hijoDer);
            }
        }
    }

    public void postorden() {
        postorden(raiz);
        System.out.println();
    }

    private void postorden(Nodo23<E> n) {
        if (n != null) {
            postorden(n.hijoIzq);
            postorden(n.hijoMedio);
            if (n.esTresNodo())
                postorden(n.hijoDer);
            System.out.print(n.valorIzq + " ");
            if (n.esTresNodo())
                System.out.print(n.valorDer + " ");
        }
    }

    public void anchura() {
        if (raiz == null)
            return;
        java.util.Queue<Nodo23<E>> cola = new java.util.LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            Nodo23<E> n = cola.poll();
            System.out.print(n.valorIzq + " ");
            if (n.esTresNodo())
                System.out.print(n.valorDer + " ");

            if (n.hijoIzq != null)
                cola.add(n.hijoIzq);
            if (n.hijoMedio != null)
                cola.add(n.hijoMedio);
            if (n.hijoDer != null)
                cola.add(n.hijoDer);
        }
        System.out.println();
    }
}
