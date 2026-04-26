package eda.solutions;

import eda.ds.AVL;

// Clase para comparar los tiempos de las dos formas de insertar en el AVL
public class TestAVL {
    public static void main(String[] args) {
        AVL<Integer> arbolRec = new AVL<>();
        AVL<Integer> arbolIter = new AVL<>();
        int numElementos = 1000000; // Vamos a meter 1 millón para que se note la diferencia

        System.out.println("Iniciando prueba con " + numElementos + " elementos...");

        // Cronometramos la versión recursiva
        long inicioRec = System.currentTimeMillis();
        for (int i = 0; i < numElementos; i++) {
            arbolRec.insertarRecursivo(i);
        }
        long finRec = System.currentTimeMillis();
        System.out.println("Tiempo inserción recursiva: " + (finRec - inicioRec) + " ms");

        // Cronometramos la versión iterativa
        long inicioIter = System.currentTimeMillis();
        for (int i = 0; i < numElementos; i++) {
            arbolIter.insertarIterativo(i);
        }
        long finIter = System.currentTimeMillis();
        System.out.println("Tiempo inserción iterativa: " + (finIter - inicioIter) + " ms");

        // Justificación de costes teóricos vs experimentales:
        // En teoría, ambas versiones (recursiva e iterativa) tienen un coste temporal
        // de O(log n)
        // porque el árbol AVL siempre mantiene la altura a raya con las rotaciones.
        // Sin embargo, en la prueba con 1.000.000 de elementos me ha salido que el
        // recursivo
        // es más del doble de rápido (77 ms frente a 162 ms). Esto pasa porque Java
        // optimiza
        // brutalmente la recursividad por debajo, mientras que en la iterativa estamos
        // usando
        // la clase Stack, que mete sobrecarga extra de memoria y procesamiento al
        // sistema.
    }
}