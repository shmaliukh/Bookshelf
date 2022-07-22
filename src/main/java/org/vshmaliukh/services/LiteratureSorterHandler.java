package org.vshmaliukh.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LiteratureSorterHandler <T> {

    List<T> literatureList;

    public LiteratureSorterHandler(List<T> literatureList){
        this.literatureList = new ArrayList<>(literatureList);
    }

    public List<T> getSortedLiterature(Comparator<T> itemsComparator) {
        return literatureList.stream()
                .sorted(itemsComparator)
                .collect(Collectors.toList());
    }
}
