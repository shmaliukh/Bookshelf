package org.vshmaliukh.handlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public interface ItemHandler<Item extends org.vshmaliukh.bookshelf.bookshelfObjects.Item> {

    ConvertorToString getConvertorToString();

    List<Item> getSortedItems(int typeOfSorting, List<Item> inputList);

    void printSortingMenu(PrintWriter printWriter);

    List<String> getTitlesList();

    List<Item> clarificationForSortingItems(List<Item> items, int userChoice, PrintWriter printWriter);

    Item getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter);

    Item getRandomItem(Random random);
}
