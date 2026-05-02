public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("--- 1 THREAD ---");
        BenchmarkSingleThread.executar();

        System.out.println("\n--- 16 THREADS ---");
        BenchmarkMultiThread.executar();
    }
}