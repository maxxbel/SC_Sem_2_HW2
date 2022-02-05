package consumersandproducers;

import java.util.Random;

public class Consumer implements Runnable{

    private final int id;
    private final  Exchange storage;
    private static final Random rnd = new Random();

    public Consumer(int id, Exchange storage) {
        this.id = id;
        this.storage = storage;
    }

    @Override
    public void run() {
        int acquiredNum;
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (storage) {

                // Если хранилище пустое то ждем пока нас не разбудят производители
                if (storage.isEmpty()) {
                    System.out.println("Потребитель номер " + id + " ждет чисел");
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                // Будим спящих производителей так как освобождаем место
                if (storage.isFull()) {
                    storage.notifyAll();
                }

                acquiredNum = storage.getNumber();
            }
            System.out.println("Потребитель номер" + id + " получил число " + acquiredNum);

            // Кулдаун, ведь мы за осознанное потребление
            int sleepTime = rnd.nextInt(100, 1000);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
