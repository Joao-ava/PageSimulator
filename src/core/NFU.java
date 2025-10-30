package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NFU {
    private final int[] pages;
    private final int partitions;
    private List<MemoryState> memoryList;
    private Map<Integer, Integer> counter;

    public NFU(int[] pages, int partitions) {
        this.pages = pages;
        this.partitions = partitions;
        this.counter = new HashMap<>();
        this.memoryList = new ArrayList<>();
    }

    public int execute() {
        int faults = 0;
        for (int page: pages) {
            if (this.counter.containsKey(page)) {
                this.counter.put(page, this.counter.get(page) + 1);
                this.memoryList.add(MemoryState.fromIterator(this.counter.values().iterator(), page, -1));
                continue;
            }

            boolean isMemoryFull = this.counter.size() == this.partitions;
            if (!isMemoryFull) {
                this.counter.put(page, 1);
                this.memoryList.add(MemoryState.fromIterator(this.counter.values().iterator(), page, -1, true));
                continue;
            }

            faults++;
            int min = Integer.MAX_VALUE;
            int outPage = -1;
            for (Map.Entry<Integer, Integer> entry : this.counter.entrySet()) {
                if (entry.getValue() < min) {
                    min = entry.getValue();
                    outPage = entry.getKey();
                }
            }
            this.counter.remove(outPage);
            this.counter.put(page, 1);
            this.memoryList.add(MemoryState.fromIterator(this.counter.keySet().iterator(), page, outPage, true));
        }
        return faults;
    }

    public List<MemoryState> getMemoryList() {
        return this.memoryList;
    }
}
