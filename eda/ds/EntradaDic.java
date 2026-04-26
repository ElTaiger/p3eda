package eda.ds;

// Una clase auxiliar para juntar la clave y el valor en un solo objeto para meterlo al árbol
public class EntradaDic<C extends Comparable<C>, V> implements Comparable<EntradaDic<C, V>> {
    C clave;
    V valor;

    public EntradaDic(C c, V v) {
        clave = c;
        valor = v;
    }

    public EntradaDic(C c) {
        this(c, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object x) {
        return ((EntradaDic<C, V>) x).clave.equals(clave);
    }

    @Override
    public int compareTo(EntradaDic<C, V> x) {
        // Comparamos siempre usando la clave, que es lo que nos importa para ordenar
        return clave.compareTo(x.clave);
    }
}