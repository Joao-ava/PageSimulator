package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemoryState {
    public List<Integer> pages;
    public int outPage;
    public int currentPage;
    public boolean isFault;

    public MemoryState(List<Integer> pages, int currentPage, int outPage, boolean isFault) {
        this.pages = pages;
        this.currentPage = currentPage;
        this.outPage = outPage;
        this.isFault = isFault;
    }

    public static MemoryState fromIterator(Iterator<Integer> pagesIterator, int currentPage, int outPage, boolean isFault) {
        List<Integer> pages = new ArrayList<>();
        while (pagesIterator.hasNext()) {
            pages.add(pagesIterator.next());
        }
        return new MemoryState(pages, currentPage, outPage, isFault);
    }

    public static MemoryState fromIterator(Iterator<Integer> pagesIterator, int currentPage, int outPage) {
        return fromIterator(pagesIterator, currentPage, outPage, false);
    }
}
