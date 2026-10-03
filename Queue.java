public class Queue {
    private int[] queue;
    private int front;
    private int rear;
    private int capacity;
    private int size;

    private static final int DEFAULT_CAPACITY = 8;

    public Queue() {
        this(DEFAULT_CAPACITY);
    }

    public Queue(int capacity) {
        this.capacity = capacity;
        this.queue = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(int item) {
        if (size == capacity) {
            resize(capacity * 2);
        }
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    public int dequeue() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        int item = queue[front];
        front = (front + 1) % capacity;
        size--;


        if (size > 0 && size == capacity / 4) {
            resize(capacity / 2);
        }

        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

   

    public int getSize() {
        return size;
    }
public int display() {
    if (size == 0) {
        System.out.println("Queue is empty");
        return -1;
    }
    System.out.print("Queue elements: ");
    for (int i = 0; i < size; i++) {
        System.out.print(queue[(front + i) % capacity]);
        if (i < size - 1) {
            System.out.print(", "); // Separates numbers with a comma and space
        }
    }
    System.out.println();
    return 0;
}

    private void resize(int newCapacity) {
        if (newCapacity < 1) {
            newCapacity = 1;
        }
        int[] newQueue = new int[newCapacity];


        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % capacity];
        }

        queue = newQueue;
        front = 0;
        rear = size - 1;
        capacity = newCapacity;
    }
}