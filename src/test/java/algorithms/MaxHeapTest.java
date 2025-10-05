package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaxHeapTest {

    @Test
    void testInsertAndExtractMax() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap heap = new MaxHeap(10, tracker);

        heap.insert(5);
        heap.insert(10);
        heap.insert(3);

        assertEquals(3, heap.size());
        assertEquals(10, heap.extractMax());
        assertEquals(2, heap.size());
        assertEquals(5, heap.extractMax());
        assertEquals(3, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testIncreaseKey() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(10);
        heap.insert(20);
        heap.insert(15);

        heap.increaseKey(2, 25);
        assertEquals(25, heap.extractMax());
    }

    @Test
    void testBuildMaxHeap() {
        int[] arr = {3, 1, 6, 5, 2, 4};
        MaxHeap heap = new MaxHeap(arr.length);
        heap.buildMaxHeap(arr);

        int[] result = heap.toArray();
        assertEquals(6, result[0]);
    }

    @Test
    void testExtractMaxFromEmptyHeapThrows() {
        MaxHeap heap = new MaxHeap(5);
        assertThrows(Exception.class, heap::extractMax);
    }

    @Test
    void testTrackerCountsOperations() {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap heap = new MaxHeap(10, tracker);

        heap.insert(10);
        heap.insert(20);
        heap.extractMax();

        assertTrue(tracker.getComparisons() > 0);
        assertTrue(tracker.getArrayAccesses() > 0);
    }

    @Test
    void testDuplicateElementsHandledCorrectly() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(3, heap.size());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
    }
}