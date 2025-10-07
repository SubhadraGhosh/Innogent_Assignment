class EvenOddPrinter {
    private int count = 1;
    private final int limit;
    private final Object lock = new Object();

    public EvenOddPrinter(int limit) {
        this.limit = limit;
    }

    public void printOdd() {
        synchronized (lock) {
            while (count <= limit) {
                while (count % 2 == 0) { // If it's an even number's turn, wait
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (count <= limit) {
                    System.out.println("Odd: " + count);
                    count++;
                }
                lock.notify(); // Notify the even thread
            }
        }
    }

    public void printEven() {
        synchronized (lock) {
            while (count <= limit) {
                while (count % 2 != 0) { // If it's an odd number's turn, wait
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (count <= limit) {
                    System.out.println("Even: " + count);
                    count++;
                }
                lock.notify(); // Notify the odd thread
            }
        }
    }
}

public class evenOddThread {
    public static void main(String[] args) {
        EvenOddPrinter printer = new EvenOddPrinter(20);

        Thread oddThread = new Thread(() -> printer.printOdd(), "OddThread");
        Thread evenThread = new Thread(() -> printer.printEven(), "EvenThread");

        oddThread.start();
        evenThread.start();
    }
}
