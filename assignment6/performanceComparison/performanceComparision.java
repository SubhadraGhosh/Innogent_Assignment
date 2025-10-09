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

//Task-7 Performance Comparison: Parallel Streams vs ExecutorService ---------------------------------------------------------

class PerformanceComparisonSimple {
    public static void main(String[] args) throws Exception {

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1_000_000; i++) numbers.add(i);

        long start1 = System.currentTimeMillis();
        long sumParallel = numbers.parallelStream()
                .mapToLong(n -> n * 2)
                .sum();
        long end1 = System.currentTimeMillis();
        System.out.println("Parallel Stream Result: " + sumParallel);
        System.out.println("Time with Parallel Stream: " + (end1 - start1) + " ms\n");

        // ----------ExecutorService ----------
        ExecutorService executor = Executors.newFixedThreadPool(4);
        int chunk = numbers.size() / 4;
        List<Future<Long>> results = new ArrayList<>();

        long start2 = System.currentTimeMillis();

        for (int i = 0; i < 4; i++) {
            int start = i * chunk;
            int end = (i == 3) ? numbers.size() : (i + 1) * chunk;
            List<Integer> subList = numbers.subList(start, end);


            Callable<Long> task = () -> {
                long sum = 0;
                for (int n : subList) sum += n * 2;
                return sum;
            };

            results.add(executor.submit(task));
        }


        long totalSum = 0;
        for (Future<Long> f : results) totalSum += f.get();

        long end2 = System.currentTimeMillis();
        executor.shutdown();

        System.out.println("ExecutorService Result: " + totalSum);
        System.out.println("Time with ExecutorService: " + (end2 - start2) + " ms");


        System.out.println("\nBoth results are same: " + (sumParallel == totalSum));
        System.out.println("Faster approach: " +
                ((end1 - start1) < (end2 - start2) ? "Parallel Stream ✅" : "ExecutorService ✅"));
    }
}