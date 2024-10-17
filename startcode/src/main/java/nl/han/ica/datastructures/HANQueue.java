package nl.han.ica.datastructures;

public class HANQueue<T> implements IHANQueue<T> {
    public HANLinkedList<T> list;

    public HANQueue() {
        list = new HANLinkedList<>();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.getSize() == 0;
    }

    @Override
    public void enqueue(T value) {
        list.insert(list.getSize(), value); // Add to the back of the queue
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T value = list.getFirst(); // Get the front element
        list.removeFirst(); // Remove the front element
        return value;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return list.getFirst(); // Return the front element without removing it
    }

    @Override
    public int getSize() {
        return list.getSize();
    }
}
