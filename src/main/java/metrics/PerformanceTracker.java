package metrics;

public class PerformanceTracker {
    private long comparisons;
    private long swaps;
    private long arrayAccesses;
    private long allocations;

    public void incComparisons() { comparisons++; }
    public void incSwaps() { swaps++; }
    public void incArrayAccesses(long n) { arrayAccesses += n; }
    public void incAllocations() { allocations++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getAllocations() { return allocations; }

    public void reset() {
        comparisons = swaps = arrayAccesses = allocations = 0;
    }
}
