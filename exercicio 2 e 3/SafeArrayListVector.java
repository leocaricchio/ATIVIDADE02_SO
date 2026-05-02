import java.util.Vector;

public class SafeArrayListVector {

    private Vector<Integer> lista;

    public SafeArrayListVector() {
        lista = new Vector<>();
    }

    public void add(Integer valor) {
        lista.add(valor);
    }

    public Integer get(int index) {
        return lista.get(index);
    }

    public void remove(int index) {
        lista.remove(index);
    }

    public int size() {
        return lista.size();
    }
}