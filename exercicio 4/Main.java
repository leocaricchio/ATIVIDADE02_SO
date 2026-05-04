public class Main {
    public static void main(String[] args) {
        DatabaseAccess db = new DatabaseAccess();

        System.out.println("--- INICIANDO TESTE DO BANCO DE DADOS ---");

        // São disparados 12 leitores (As 2 últimas devem ficar bloqueadas esperando)
        for (int i = 1; i <= 12; i++) {
            final int id = i;
            new Thread(() -> db.read(id), "Thread-Leitor-" + String.format("%02d", i)).start();
        }

        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // São disparados operações de Escrita
        new Thread(() -> db.create(100, "Dado A"), "Thread-Escritor-1").start();
        new Thread(() -> db.update(100, "Dado Modificado"), "Thread-Escritor-2").start();

        // São disparados mais leitores 
        for (int i = 13; i <= 15; i++) {
            final int id = i;
            new Thread(() -> db.read(id), "Thread-Leitor-" + String.format("%02d", i)).start();
        }
        
        // Última escrita
        new Thread(() -> db.delete(100), "Thread-Escritor-3").start();
    }
}
