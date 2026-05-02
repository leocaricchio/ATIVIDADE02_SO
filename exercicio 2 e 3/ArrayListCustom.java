public class ArrayListCustom<T> {

    private Object[] elementos;
    private int tamanho;

    public ArrayListCustom(int capacidade) {
        elementos = new Object[capacidade];
        tamanho = 0;
    }

    public void add(T valor) {
        if (tamanho == elementos.length) resize();
        elementos[tamanho++] = valor;
    }

    public T get(int index) {
        return (T) elementos[index];
    }

    public void remove(int index) {
        for (int i = index; i < tamanho - 1; i++)
            elementos[i] = elementos[i + 1];
        tamanho--;
    }

    public int size() {
        return tamanho;
    }

    private void resize() {
        Object[] novo = new Object[elementos.length * 2];
        System.arraycopy(elementos, 0, novo, 0, elementos.length);
        elementos = novo;
    }
}