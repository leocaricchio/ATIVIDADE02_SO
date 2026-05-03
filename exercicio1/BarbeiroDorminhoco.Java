import java.util.concurrent.Semaphore;
import java.util.Random;

public class BarbeiroDorminhoco {

    static Semaphore customers = new Semaphore(0);
    static Semaphore barbers = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);

    static int waiting = 0;
    static final int CHAIRS = 10;

    static Random random = new Random();

    public static void main(String[] args) {

        //Criação dos Barbeiros
        for (int i = 1; i <= 2; i++) {
            int id = i;
            new Thread(() -> Barbers(id)).start();
        }

        //Gerador de Clientes
        new Thread(() -> generateCustomers()).start();
    }

    //Ação do Barbeiro
    static void Barbers(int id) {
        while (true) {
            try {
                customers.acquire();
                mutex.acquire();
                waiting = waiting - 1;
                System.out.println("Barbeiro " + id + " chamou cliente. Esperando: " + waiting);
                barbers.release();
                mutex.release();
                cutHair(id);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Ação do Cliente
    static void Customers(int id) {
        try {
            mutex.acquire();

            if (waiting < CHAIRS) {
                waiting = waiting + 1;
                System.out.println("Cliente " + id + " entrou. Esperando: " + waiting);

                customers.release();
                mutex.release();

                barbers.acquire();
                getHairCut(id);

            } else {
                System.out.println("Cliente " + id + " foi embora (fila cheia)");
                mutex.release(); 
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Controle de chegada dos clientes
    static void generateCustomers() {
        int id = 1;

        while (true) {
            try {
                double tempo = 4 + (2 * random.nextDouble());
                Thread.sleep((long)(tempo * 1000));

                int finalId = id;
                new Thread(() -> Customers(finalId)).start();

                id++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Controle do tempo de corte de cabelo
    static void cutHair(int barberId) throws InterruptedException {
    double tempo = 5 + (10 * random.nextDouble());

    System.out.printf("Barbeiro %d cortando cabelo (%.2f s)\n", barberId, tempo);

    Thread.sleep((long)(tempo * 1000));

    System.out.println("Barbeiro " + barberId + " terminou corte");
}

    static void getHairCut(int clientId) {
        System.out.println("Cliente " + clientId + " está cortando o cabelo");
    }
}
