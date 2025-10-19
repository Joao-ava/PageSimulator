package core;

import java.util.LinkedList;

public class LRU {
    private int[] pages;
    private int partitions;
    private final LinkedList<Integer> usages;

    public LRU(int[] pages, int partitions) {
        this.pages = pages;
        this.partitions = partitions;
        this.usages = new LinkedList<>();
    }

    public int execute() {
        int faults = 0;
        for (int page: pages) {
            int index = this.usages.indexOf(page);
            if (index != -1 || !this.isMemoryFull()) {
                this.registerUse(page, index);
                continue;
            }
            this.usages.removeLast();
            faults++;
            this.registerUse(page, -1);
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
}
