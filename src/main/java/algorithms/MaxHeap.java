package algorithms;

import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MaxHeap {

    private int[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = tracker;
    }

    public MaxHeap(int capacity) {
        this(capacity, new PerformanceTracker());
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }

    public void insert(int key) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
            tracker.incAllocations();
        }

        int i = size;
        heap[size++] = key;
        tracker.incArrayAccesses(1);

        while (i > 0 && compare(heap[i], heap[parent(i)]) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public void increaseKey(int index, int newKey) {
        if (index < 0 || index >= size) throw new IllegalArgumentException("Invalid index");
        tracker.incComparisons();
        if (newKey < heap[index]) {
            throw new IllegalArgumentException("New key must be larger than current key");
        }

        heap[index] = newKey;
        tracker.incArrayAccesses(1);

        while (index > 0 && compare(heap[index], heap[parent(index)]) > 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    public int extractMax() {
        if (size <= 0) throw new NoSuchElementException("Heap is empty");
        if (size == 1) return heap[--size];

        int root = heap[0];
        heap[0] = heap[--size];
        tracker.incArrayAccesses(2);

        maxHeapify(0);
        return root;
    }

    private void maxHeapify(int i) {
        int largest = i;
        int left = left(i);
        int right = right(i);

        if (left < size && compare(heap[left], heap[largest]) > 0) {
            largest = left;
        }

        if (right < size && compare(heap[right], heap[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    public void buildMaxHeap(int[] arr) {
        heap = Arrays.copyOf(arr, arr.length);
        size = arr.length;
        tracker.incAllocations();

        for (int i = size / 2 - 1; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    private void swap(int i, int j) {
        tracker.incSwaps();
        tracker.incArrayAccesses(4);
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int compare(int a, int b) {
        tracker.incComparisons();
        return Integer.compare(a, b);
    }

    public int[] toArray() {
        return Arrays.copyOf(heap, size);
    }

    public PerformanceTracker getTracker() {
        return tracker;
    }
}