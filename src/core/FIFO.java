package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FIFO {
    private int[] pages;
    private int partitions;
    private final List<MemoryState> memoryList;
    private Queue<Integer> memory = new LinkedList<>();

    public FIFO(int[] pages, int partitions) {
        this.pages = pages;
        this.partitions = partitions;
        this.memoryList = new ArrayList<>();
    }

    public int execute() {
        int faults = 0;
        for(int page: pages){
            // se a pagina nao estiver na memoria, incementar faults
            if(!memory.contains(page)){
                faults++;
                // se a memoria estiver cheia, remover a pagina mais antiga (primeira da fila)
                int out = -1;
                if(isMemoryFull()){
                    out = memory.remove();
                }
                // adicionar nova pagina
                memory.add(page);
                this.memoryList.add(MemoryState.fromIterator(this.memory.iterator(), page, out, true));
                continue;
            }
            this.memoryList.add(MemoryState.fromIterator(this.memory.iterator(), page, -1, false));
        }
        return faults;
    }

    private boolean isMemoryFull() {
        return this.memory.size() == this.partitions;
    }

    public List<MemoryState> getMemoryList() {
        return this.memoryList;
    }
}