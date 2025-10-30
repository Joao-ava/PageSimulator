package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemoryState {
    public List<Integer> pages;
    public int outPage;
    public int currentPage;

    public MemoryState(List<Integer> pages, int currentPage) {
        this(pages, currentPage, -1);
    }

    public MemoryState(List<Integer> pages, int currentPage, int outPage) {
        this.pages = pages;
        this.currentPage = currentPage;
        this.outPage = outPage;
    }

    public static MemoryState fromIterator(Iterator<Integer> pagesIterator, int currentPage, int outPage) {
        List<Integer> pages = new ArrayList<>();
        while (pagesIterator.hasNext()) {
            pages.add(pagesIterator.next());
        }
        return new MemoryState(pages, currentPage, outPage);
    }
}
