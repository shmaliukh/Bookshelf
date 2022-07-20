package org.vshmaliukh.services;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LiteratureSorterHandler <T extends Item> {

    List<T> literatureList;

    public LiteratureSorterHandler(List<T> literatureList){
        this.literatureList = new ArrayList<>(literatureList);
    }

    public List<T> getSortedLiterature(Comparator<T> bookComparator) {
        return literatureList.stream()
                .sorted(bookComparator)
                .collect(Collectors.toList());
    }
}
