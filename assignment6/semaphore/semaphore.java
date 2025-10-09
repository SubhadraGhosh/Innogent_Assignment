//Task 6  Semaphore for Limited Resource Access
import java.util.*;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.*;

class ParkingLot {
    private final Semaphore sem;

    ParkingLot(int spots) {
        sem = new Semaphore(spots);
    }

    public void park(String car) {
        try {
            System.out.println(car + " waiting to park...");
            sem.acquire(); // take a permit
            System.out.println(car + " parked!");
            Thread.sleep(1000);
        } catch (Exception e) {}
        finally {
            System.out.println(car + " leaving...");
            sem.release(); // free the spot
        }
    }
}

class SemaphoreEasy {
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(2); // only 2 spots

        for (int i = 1; i <= 5; i++) {
            String car = "Car-" + i;
            new Thread(() -> lot.park(car)).start();
        }
    }
}