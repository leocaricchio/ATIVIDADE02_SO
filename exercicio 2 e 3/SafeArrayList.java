import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SafeArrayList<T> {

    private Object[] elementos;
    private int tamanho;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public SafeArrayList(int capacidade) {
        elementos = new Object[capacidade];
        tamanho = 0;
    }

    public void add(T valor) {
        lock.writeLock().lock();
        try {
            if (tamanho == elementos.length) resize();
            elementos[tamanho++] = valor;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public T get(int index) {
        lock.readLock().lock();
        try {
            return (T) elementos[index];
        } finally {
            lock.readLock().unlock();
        }
    }

    public void remove(int index) {
        lock.writeLock().lock();
        try {
            for (int i = index; i < tamanho - 1; i++)
                elementos[i] = elementos[i + 1];
            tamanho--;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return tamanho;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void resize() {
        Object[] novo = new Object[elementos.length * 2];
        System.arraycopy(elementos, 0, novo, 0, elementos.length);
        elementos = novo;
    }
}