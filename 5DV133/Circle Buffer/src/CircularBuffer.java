import java.util.NoSuchElementException;

/**
 * Queue for ints implemented with a circular buffer.
 */
public class CircularBuffer {
    private int maxSize;
    private int front = 0;
    private int rear = 0;
    private int elements = 0;
    private int[] list;
    /**
     * Creates a new circular buffer with a given size;
     * @param size The size of the buffer.
     * @throws IllegalArgumentException If the size is smaller than 1.
     */
    public CircularBuffer(int size) throws IllegalArgumentException {
        if (size < 1) {
            throw  new IllegalArgumentException("NO!");
        }
        maxSize = size;
        list  = new int[maxSize];
    }

    /**
     * Adds an element to the buffer.
     * @param i The element to add.
     * @throws IllegalStateException If the buffer is full.
     */
    public void put(int i) {
        if (elements == maxSize){
            throw  new IllegalStateException("NO!");
        }
        this.elements++;
        rear = (rear + 1) % maxSize;
        list[rear] = i;

    }

    /**
     * Returns and removes the first element in the buffer.
     * @return The first element in the buffer.
     * @throws NoSuchElementException If the buffer is empty.
     */
    public int take() {
        if(elements == 0) {
            throw new NoSuchElementException("NO!");
        }
        elements--;
        front = (front + 1) % maxSize;
        return list[front];
    }

    /**
     * @return The current number of elements in the queue.
     */
    public int size() {
        return this.elements;
    }

    /**
     * @return The maximum number of elements in the queue.
     */
    public int maxSize() {
        return this.maxSize;
    }
}
