package eda.ds;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class AVL<E extends Comparable<E>> {
    NodoAVL<E> raiz = null;

    public AVL() {
    }

    // Auxiliares para controlar la altura sin que pete si es null
    private int altura(NodoAVL<E> n) {
        if (n == null)
            return 0;
        return n.altura;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Calcula si el árbol se está yendo de lado
    private int getFactorEquilibrio(NodoAVL<E> n) {
        if (n == null)
            return 0;
        return altura(n.izq) - altura(n.der);
    }

    // Rotaciones para equilibrar el árbol
    private NodoAVL<E> rotacionDerecha(NodoAVL<E> y) {
        NodoAVL<E> x = y.izq;
        NodoAVL<E> T2 = x.der;

        // Giramos
        x.der = y;
        y.izq = T2;

        // Actualizamos alturas después de mover los nodos
        y.altura = max(altura(y.izq), altura(y.der)) + 1;
        x.altura = max(altura(x.izq), altura(x.der)) + 1;

        return x;
    }

    private NodoAVL<E> rotacionIzquierda(NodoAVL<E> x) {
        NodoAVL<E> y = x.der;
        NodoAVL<E> T2 = y.izq;

        // Giramos
        y.izq = x;
        x.der = T2;

        // Actualizamos alturas
        x.altura = max(altura(x.izq), altura(x.der)) + 1;
        y.altura = max(altura(y.izq), altura(y.der)) + 1;

        return y;
    }

    // Método que mira si hace falta hacer rotación simple o doble
    private NodoAVL<E> balancear(NodoAVL<E> n) {
        if (n == null)
            return null;

        int balance = getFactorEquilibrio(n);

        // Desbalanceado por la izquierda
        if (balance > 1 && getFactorEquilibrio(n.izq) >= 0)
            return rotacionDerecha(n);

        // Caso chungo: desbalanceado izq-der (rotación doble)
        if (balance > 1 && getFactorEquilibrio(n.izq) < 0) {
            n.izq = rotacionIzquierda(n.izq);
            return rotacionDerecha(n);
        }

        // Desbalanceado por la derecha
        if (balance < -1 && getFactorEquilibrio(n.der) <= 0)
            return rotacionIzquierda(n);

        // Caso chungo: desbalanceado der-izq (rotación doble)
        if (balance < -1 && getFactorEquilibrio(n.der) > 0) {
            n.der = rotacionDerecha(n.der);
            return rotacionIzquierda(n);
        }

        return n; // Si ya está bien, lo dejamos tal cual
    }

    // La versión de inserción que se llama a sí misma (recursiva)
    public void insertarRecursivo(E dato) {
        raiz = insertarRecursivo(dato, raiz);
    }

    private NodoAVL<E> insertarRecursivo(E dato, NodoAVL<E> n) {
        if (n == null)
            return new NodoAVL<>(dato);

        int cmp = dato.compareTo(n.dato);
        if (cmp < 0) {
            n.izq = insertarRecursivo(dato, n.izq);
        } else if (cmp > 0) {
            n.der = insertarRecursivo(dato, n.der);
        } else {
            return n; // Pasamos de los repetidos
        }

        n.altura = 1 + max(altura(n.izq), altura(n.der));
        return balancear(n);
    }

    // La versión de inserción con bucle (iterativa) usando una pila
    public void insertarIterativo(E dato) {
        if (raiz == null) {
            raiz = new NodoAVL<>(dato);
            return;
        }

        NodoAVL<E> actual = raiz;
        NodoAVL<E> padre = null;
        Stack<NodoAVL<E>> camino = new Stack<>(); // Aquí nos guardamos por dónde vamos bajando

        // Bajamos hasta dar con el hueco
        while (actual != null) {
            padre = actual;
            camino.push(padre);
            int cmp = dato.compareTo(actual.dato);
            if (cmp < 0)
                actual = actual.izq;
            else if (cmp > 0)
                actual = actual.der;
            else
                return; // Repetido
        }

        // Metemos el nodo nuevo
        NodoAVL<E> nuevo = new NodoAVL<>(dato);
        if (dato.compareTo(padre.dato) < 0)
            padre.izq = nuevo;
        else
            padre.der = nuevo;

        // Hacemos el camino de vuelta hacia arriba usando la pila para actualizar y
        // balancear
        while (!camino.isEmpty()) {
            NodoAVL<E> n = camino.pop();
            n.altura = 1 + max(altura(n.izq), altura(n.der));

            NodoAVL<E> balanceado = balancear(n);

            if (camino.isEmpty()) {
                raiz = balanceado;
            } else {
                NodoAVL<E> nPadre = camino.peek();
                if (nPadre.izq == n)
                    nPadre.izq = balanceado;
                else
                    nPadre.der = balanceado;
            }
        }
    }

    // --- RECORRIDOS PARA EL AVL ---
    public void preorden() {
        preorden(raiz);
        System.out.println();
    }

    private void preorden(NodoAVL<E> n) {
        if (n != null) {
            System.out.print(n.dato + " ");
            preorden(n.izq);
            preorden(n.der);
        }
    }

    public void inorden() {
        inorden(raiz);
        System.out.println();
    }

    private void inorden(NodoAVL<E> n) {
        if (n != null) {
            inorden(n.izq);
            System.out.print(n.dato + " ");
            inorden(n.der);
        }
    }

    public void postorden() {
        postorden(raiz);
        System.out.println();
    }

    private void postorden(NodoAVL<E> n) {
        if (n != null) {
            postorden(n.izq);
            postorden(n.der);
            System.out.print(n.dato + " ");
        }
    }

    public void anchura() {
        if (raiz == null)
            return;
        java.util.Queue<NodoAVL<E>> cola = new java.util.LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            NodoAVL<E> n = cola.poll();
            System.out.print(n.dato + " ");
            if (n.izq != null)
                cola.add(n.izq);
            if (n.der != null)
                cola.add(n.der);
        }
        System.out.println();
    }

}