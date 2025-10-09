// 4.Using executor service and callable
import java.io.*;
import java.util.*;
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


class CountFileLines implements Callable<Integer> {
    File file;
    CountFileLines(File file) { this.file = file; }

    public Integer call() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) count++;
        } catch (Exception e) {}
        System.out.println(file.getName() + " → " + count + " lines");
        return count;
    }
}

class ExecutorEasy {
    public static void main(String[] args) throws Exception {
        File folder = new File("C:/Users/THINKPAD L460/Desktop/Innogent/assignment3/");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<Integer>> results = new ArrayList<>();
        int total = 0;

        for (File f : files)
            results.add(service.submit(new CountFileLines(f)));
        for (Future<Integer> f : results) total += f.get();
        service.shutdown();
        System.out.println("Total lines in all files: " + total);
    }
}