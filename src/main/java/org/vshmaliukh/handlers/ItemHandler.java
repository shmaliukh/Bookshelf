package org.vshmaliukh.handlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public interface ItemHandler<T extends Item> {

    ConvertorToString getConvertorToString();

    List<T> getSortedItems(int typeOfSorting, List<T> inputList);

    void printSortingMenu(PrintWriter printWriter);

    List<T> clarificationForSortingItems(List<T> items, int userChoice, PrintWriter printWriter);

    T getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter);

    T getRandomItem(Random random);
}
