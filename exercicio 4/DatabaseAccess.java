public class DatabaseAccess {
    
    private int activeReaders = 0;
    private int activeWriters = 0;
    private int waitingWriters = 0;

    public void create(int id, String data) {
        executeWriteOperation("CREATE (Inserir ID " + id + " -> " + data + ")");
    }

    public void read(int id) {
        executeReadOperation("READ (Consultar ID " + id + ")");
    }

    public void update(int id, String data) {
        executeWriteOperation("UPDATE (Atualizar ID " + id + " -> " + data + ")");
    }

    public void delete(int id) {
        executeWriteOperation("DELETE (Remover ID " + id + ")");
    }

    private synchronized void beforeRead() throws InterruptedException {
        // Bloqueia a leitura se:
        //  Tiver algum escritor ativo
        //  Tiver escritor esperando na fila (dá prioridade para a escrita não sofrer starvation)
        //  Já houver 10 leitores ativos
        while (activeWriters > 0 || waitingWriters > 0 || activeReaders >= 10) {
            wait(); 
        }
        activeReaders++;
    }

    private synchronized void afterRead() {
        activeReaders--;
        notifyAll(); 
    }

    private void executeReadOperation(String operationName) {
        try {
            beforeRead(); 
            
            System.out.println("[LEITURA] " + Thread.currentThread().getName() 
                    + " iniciou: " + operationName + " | Leitores ativos: " + activeReaders);
            
            Thread.sleep((long) (Math.random() * 2000 + 1000)); 
            
            System.out.println("[LEITURA] " + Thread.currentThread().getName() + " finalizou: " + operationName);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            afterRead();
        }
    }

    private synchronized void beforeWrite() throws InterruptedException {
        waitingWriters++;
        
        // Bloqueia a escrita se:
        //  Houver leitores ativos
        //  Houver outro escritor ativo
        while (activeReaders > 0 || activeWriters > 0) {
            wait(); 
        }
        
        waitingWriters--; 
        activeWriters++;  
    }

    private synchronized void afterWrite() {
        activeWriters--;
        notifyAll(); 
    }

    private void executeWriteOperation(String operationName) {
        try {
            beforeWrite(); 
            
            System.out.println(">>> [ESCRITA EXCLUSIVA] " + Thread.currentThread().getName() + " INICIOU: " + operationName);
            
            Thread.sleep((long) (Math.random() * 3000 + 2000)); 
            
            System.out.println("<<< [ESCRITA EXCLUSIVA] " + Thread.currentThread().getName() + " FINALIZOU: " + operationName);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            afterWrite(); 
        }
    }
}
