package core;
import java.util.LinkedList;
import java.util.List;

public class Clock {
    private int[] pages;
    private int partitions;
    private int[] memory;
    private boolean[] useBit;
    private int pointer = 0;
    private final List<MemoryState> memoryList;

    public Clock(int[] pages, int partitions) {
        this.pages = pages;
        this.partitions = partitions;
        this.memory = new int[partitions];
        this.useBit = new boolean[partitions];
        this.memoryList = new LinkedList<>();
        for (int i = 0; i < partitions; i++) {
            memory[i] = -1;
            useBit[i] = false;
        }
    }


    public int execute(){
        int faults = 0;
        for(int page: pages){
            if(!pageInMemory(page)){
                faults++;

                while(true){
                    // substituir se o bit for falso
                    if(!useBit[pointer]){
                        int out = memory[pointer];
                        memory[pointer] = page;
                        useBit[pointer] = true;
                        pointer = (pointer + 1) % partitions;
                        memoryList.add(new MemoryState(this.getMemory(), page, out, true));
                        break;
                    } else {
                        //segunda chance
                        useBit[pointer] = false;
                        pointer = (pointer + 1) % partitions;
                    }
                }
            } else {
                for(int i = 0; i < partitions; i++){
                    if(memory[i] == page){
                        useBit[i] = true;
                        break;
                    }
                }
                memoryList.add(new MemoryState(this.getMemory(), page, -1, false));
            }
        }
        return faults;
    }

    private boolean pageInMemory(int page){
        for(int i = 0; i < partitions; i++){
            if(memory[i] == page){
                return true;
            }
        }
        return false;
    }

    private List<Integer> getMemory() {
        List<Integer> internalMemory = new LinkedList<>();
        for(int i = 0; i < memory.length; i++){
            internalMemory.add(memory[i]);
        }
        return internalMemory;
    }

    public List<MemoryState> getMemoryList() {
        return this.memoryList;
    }
}
