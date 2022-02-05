package consumersandproducers;

import java.util.Random;

public class Producer implements Runnable{

    private final int id;
    private final Exchange storage;
    private static final Random rnd = new Random();

    public Producer(int id, Exchange storage) {
        this.id = id;
        this.storage = storage;
    }

    @Override
    public void run() {
        int generatedNum = rnd.nextInt();
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (storage) {

                // Если хранилище полное то ждем пока нас не разбудят потребители
                if (storage.isFull()) {
                    System.out.println("Производитель номер " + id + " ждет место");
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                // Будим спящих потребителей так как кладем чиселку
                if (storage.isEmpty()) {
                    storage.notifyAll();
                }

                storage.putNumber(generatedNum);
            }
            System.out.println("Производитель номер " + id + " произвел число " + generatedNum);

            // Кулдаун, ведь мы за осознанное производство
            int sleepTime = rnd.nextInt(100, 500);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
