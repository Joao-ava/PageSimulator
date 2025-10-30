package core;

import java.util.LinkedList;
import java.util.List;

public class LRU {
    private int[] pages;
    private int partitions;
    private final LinkedList<Integer> usages;
    private final List<MemoryState> memoryList;

    public LRU(int[] pages, int partitions) {
        this.pages = pages;
        this.partitions = partitions;
        this.usages = new LinkedList<>();
        this.memoryList = new LinkedList<>();
    }

    public int execute() {
        int faults = 0;
        for (int page: pages) {
            int index = this.usages.indexOf(page);
            if (index != -1 || !this.isMemoryFull()) {
                this.registerUse(page, index);
                boolean isFault = index == -1;
                if (isFault) {
                    faults++;
                }
                this.memoryList.add(MemoryState.fromIterator(usages.iterator(), page, -1, isFault));
                continue;
            }
            int out = this.usages.removeLast();
            faults++;
            this.registerUse(page, -1);
            this.memoryList.add(MemoryState.fromIterator(usages.iterator(), page, out, true));
        }
        return faults;
    }

    private boolean isMemoryFull() {
        return this.usages.size() == this.partitions;
    }

    private void registerUse(int page, int index) {
        boolean isHit = index != -1;
        if (isHit) {
            this.usages.remove(index);
        }
        this.usages.addFirst(page);
    }

    public List<MemoryState> getMemoryList() {
        return this.memoryList;
    }
}
