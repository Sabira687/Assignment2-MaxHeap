package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};
        String outputFile = "benchmark_results.csv";

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("n,time_ns,comparisons,swaps,array_accesses,allocations\n");

            for (int n : sizes) {
                System.out.println("Running benchmark for n = " + n);

                int[] arr = generateRandomArray(n);
                PerformanceTracker tracker = new PerformanceTracker();
                MaxHeap heap = new MaxHeap(n, tracker);

                long start = System.nanoTime();

                heap.buildMaxHeap(arr);

                long end = System.nanoTime();
                long time = end - start;

                writer.write(String.format("%d,%d,%d,%d,%d,%d\n",
                        n,
                        time,
                        tracker.getComparisons(),
                        tracker.getSwaps(),
                        tracker.getArrayAccesses(),
                        tracker.getAllocations()));

                System.out.printf("n=%d done (time: %d ns)%n", n, time);
            }

            System.out.println("\nResults saved to " + outputFile);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static int[] generateRandomArray(int n) {
        Random random = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(n * 10);
        }
        return arr;
    }
}