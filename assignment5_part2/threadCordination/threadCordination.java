//--------------------------------Thread Coordination with CountDownLatch--------------------------------------
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

class Worker extends Thread {
    CountDownLatch latch;

    Worker(CountDownLatch latch, String name) {
        super(name);
        this.latch = latch;
    }

    public void run() {

        System.out.println(getName() + " is working...");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        System.out.println(getName() + " finished.");
        latch.countDown();
    }
}

class LatchEasy {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        new Worker(latch, "Worker-1").start();
        new Worker(latch, "Worker-2").start();
        new Worker(latch, "Worker-3").start();

        latch.await(); // main thread waits
        System.out.println("All workers finished, main thread continues...");
    }
}