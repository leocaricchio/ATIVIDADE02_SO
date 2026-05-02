import java.util.Random;

public class BenchmarkSingleThread {

    public static void executar() {

        int[] tamanhos = {1000, 10000, 50000};

        for (int n : tamanhos) {

            ArrayListCustom<Integer> normal = new ArrayListCustom<>(10);
            SafeArrayList<Integer> safe = new SafeArrayList<>(10);

            long tAddNormal = tempoInsercaoNormal(normal, n);
            long tAddSafe = tempoInsercaoSafe(safe, n);

            long tBuscaNormal = tempoBuscaNormal(normal, n);
            long tBuscaSafe = tempoBuscaSafe(safe, n);

            long tRemoveNormal = tempoRemocaoNormal(normal, n);
            long tRemoveSafe = tempoRemocaoSafe(safe, n);

            System.out.println("\n=== Numero de operações " + n + " ===");

            print("Inserção", tAddNormal, tAddSafe, n);
            print("Busca", tBuscaNormal, tBuscaSafe, n);
            print("Remoção", tRemoveNormal, tRemoveSafe, n);
        }
    }

    // -------- INSERÇÃO --------
    private static long tempoInsercaoNormal(ArrayListCustom<Integer> l, int n) {
        long ini = System.nanoTime();
        for (int i = 0; i < n; i++) l.add(i);
        return System.nanoTime() - ini;
    }

    private static long tempoInsercaoSafe(SafeArrayList<Integer> l, int n) {
        long ini = System.nanoTime();
        for (int i = 0; i < n; i++) l.add(i);
        return System.nanoTime() - ini;
    }

    // -------- BUSCA --------
    private static long tempoBuscaNormal(ArrayListCustom<Integer> l, int n) {
        Random r = new Random();
        long ini = System.nanoTime();
        for (int i = 0; i < n; i++)
            l.get(r.nextInt(l.size()));
        return System.nanoTime() - ini;
    }

    private static long tempoBuscaSafe(SafeArrayList<Integer> l, int n) {
        Random r = new Random();
        long ini = System.nanoTime();
        for (int i = 0; i < n; i++)
            l.get(r.nextInt(l.size()));
        return System.nanoTime() - ini;
    }

    // -------- REMOÇÃO --------
    private static long tempoRemocaoNormal(ArrayListCustom<Integer> l, int n) {
        long ini = System.nanoTime();
        for (int i = 0; i < n; i++)
            l.remove(l.size() - 1);
        return System.nanoTime() - ini;
    }

    private static long tempoRemocaoSafe(SafeArrayList<Integer> l, int n) {
        long ini = System.nanoTime();
        for (int i = 0; i < n; i++)
            l.remove(l.size() - 1);
        return System.nanoTime() - ini;
    }

    // -------- PRINT --------
    private static void print(String op, long t1, long t2, int n) {

        double tempo1Ms = t1 / 1_000_000.0;
        double tempo2Ms = t2 / 1_000_000.0;

        double ops1 = n / (t1 / 1_000_000_000.0);
        double ops2 = n / (t2 / 1_000_000_000.0);

        System.out.println(op);
        System.out.println("ArrayList: " + tempo1Ms + " ms | " + ops1 + " ops/s");
        System.out.println("SafeArrayList: " + tempo2Ms + " ms | " + ops2 + " ops/s");
    }
}