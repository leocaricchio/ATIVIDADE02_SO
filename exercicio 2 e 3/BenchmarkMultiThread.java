public class BenchmarkMultiThread {

    public static void executar() throws InterruptedException {

        int[] tamanhos = {1000, 10000, 50000};
        int threads = 16;

        for (int n : tamanhos) {

            int opsPorThread = n / threads;

            SafeArrayList<Integer> safe = new SafeArrayList<>(10);
            SafeArrayListVector vector = new SafeArrayListVector();

            System.out.println("\n=== Numero de operações " + n + " (16 THREADS) ===");

            long tAddSafe = rodar(safe, threads, opsPorThread, 0);
            long tAddVector = rodar(vector, threads, opsPorThread, 0);

            long tBuscaSafe = rodar(safe, threads, opsPorThread, 1);
            long tBuscaVector = rodar(vector, threads, opsPorThread, 1);

            long tRemoveSafe = rodar(safe, threads, opsPorThread, 2);
            long tRemoveVector = rodar(vector, threads, opsPorThread, 2);

            print("Inserção", tAddSafe, tAddVector, n);
            print("Busca", tBuscaSafe, tBuscaVector, n);
            print("Remoção", tRemoveSafe, tRemoveVector, n);
        }
    }

    private static long rodar(Object lista, int threads, int ops, int tipo)
            throws InterruptedException {

        Thread[] t = new Thread[threads];

        long ini = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            t[i] = new Worker(lista, ops, tipo);
            t[i].start();
        }

        for (Thread th : t) th.join();

        return System.nanoTime() - ini;
    }

    private static void print(String op, long t1, long t2, int totalOps) {

        double tempo1Ms = t1 / 1_000_000.0;
        double tempo2Ms = t2 / 1_000_000.0;

        double ops1 = totalOps / (t1 / 1_000_000_000.0);
        double ops2 = totalOps / (t2 / 1_000_000_000.0);

        System.out.println(op);
        System.out.println("SafeArrayList: " + tempo1Ms + " ms | " + ops1 + " ops/s");
        System.out.println("Vector: " + tempo2Ms + " ms | " + ops2 + " ops/s");
    }
}