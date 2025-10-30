package core;

import java.util.ArrayList;
import java.util.List;

public class FIFO {
    private int[] pages;
    private int partitions;
    private final List<MemoryState> memoryList;

    public FIFO(int[] pages, int partitions) {
        this.pages = pages;
        this.partitions = partitions;
        this.memoryList = new ArrayList<>();
    }

    public int execute() {
        return 0;
    }

    public List<MemoryState> getMemoryList() {
        return this.memoryList;
    }
}