package org.vshmaliukh.handlers;

import java.io.PrintWriter;
import java.util.List;

public interface ItemHandler<T>{

    List<T> getSortedItems(int typeOfSorting, List<T> inputList);

    void printSortingMenu(PrintWriter printWriter);
}
