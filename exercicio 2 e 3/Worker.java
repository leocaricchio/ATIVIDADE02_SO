import java.util.Random;

public class Worker extends Thread {

    private Object lista;
    private int ops;
    private int tipo; // 0=add, 1=busca, 2=remove

    public Worker(Object lista, int ops, int tipo) {
        this.lista = lista;
        this.ops = ops;
        this.tipo = tipo;
    }

    public void run() {
        Random r = new Random();

        for (int i = 0; i < ops; i++) {

            try {
                if (lista instanceof SafeArrayList) {
                    SafeArrayList<Integer> l = (SafeArrayList<Integer>) lista;

                    if (tipo == 0) l.add(r.nextInt());
                    else if (tipo == 1 && l.size() > 0)
                        l.get(r.nextInt(l.size()));
                    else if (tipo == 2 && l.size() > 0)
                        l.remove(l.size() - 1);

                } else if (lista instanceof SafeArrayListVector) {
                    SafeArrayListVector l = (SafeArrayListVector) lista;

                    if (tipo == 0) l.add(r.nextInt());
                    else if (tipo == 1 && l.size() > 0)
                        l.get(r.nextInt(l.size()));
                    else if (tipo == 2 && l.size() > 0)
                        l.remove(l.size() - 1);
                }

            } catch (Exception e) {}
        }
    }
}