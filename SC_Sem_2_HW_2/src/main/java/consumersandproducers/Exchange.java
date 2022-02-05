package consumersandproducers;

import java.util.LinkedList;
import java.util.List;

public class Exchange {
    private final List<Integer> storage;
    private final int capacity;

    public Exchange(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number");
        }
        this.capacity = capacity;
        storage = new LinkedList<>();
    }

    public void putNumber(int number) {
        storage.add(number);
    }
    public int getNumber() {
        return storage.remove(0);
    }
    public boolean isFull() {
        return storage.size() >= capacity;
    }
    public boolean isEmpty() {
        return storage.isEmpty();
    }

}
