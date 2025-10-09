//Task 5 ReadWriteLock for Shared Resource Access
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

class SharedData {

    private String data = "Initial Data";
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void readData(String name) {
        lock.readLock().lock();
        try {
            System.out.println(name + " reading: " + data);
            Thread.sleep(500);
        } catch (Exception e) {}
        finally { lock.readLock().unlock(); }
    }

    public void writeData(String name, String newData) {
        lock.writeLock().lock();
        try {
            System.out.println(name + " writing...");
            Thread.sleep(1000);
            data = newData;
            System.out.println(name + " updated data to: " + data);
        } catch (Exception e) {}
        finally { lock.writeLock().unlock(); }
    }
}

class ReadWriteEasy {
    public static void main(String[] args) {
        SharedData sd = new SharedData();

        new Thread(() -> sd.readData("Reader-1")).start();
        new Thread(() -> sd.readData("Reader-2")).start();
        new Thread(() -> sd.writeData("Writer", "New Data Value")).start();
    }
}