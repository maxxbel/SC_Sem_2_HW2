package consumersandproducers;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExampleMain {

    public static final int NUMBER_OF_THREADS = 2;
    private static final int CAPACITY = 4;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        Exchange exchange = new Exchange(CAPACITY);

        for (int i = 0; i < NUMBER_OF_THREADS / 2; i++) {
            service.submit(new Consumer(i, exchange));
            service.submit(new Producer(i, exchange));
        }

        service.shutdown();
        // он будет ехать вечно, надо как то интерраптить треды в сервисе, или просто переписать на фор
        // но так как в задании было написать 2 класса а это просто дравер к ним, то нормас, пусть бесконечно мчатся
    }
}
