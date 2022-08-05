package org.vshmaliukh.web;

import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.Shelf;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.terminal.services.print_table_service.ConvertorToStringForLiterature;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class WebShelfHandler extends Terminal {

    public WebShelfHandler(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.shelf = new Shelf(null); // TODO
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;

        setUpGsonHandler();
        readShelfItemsFromJson();
    }

    public static String generateTableOfShelfItems(List<Item> shelfLiteratureObjects, boolean isNeedIndex, boolean isForEditing) {
        if (shelfLiteratureObjects.isEmpty()) {
            return "No available literature of Shelf to print";
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); // TODO remove baos
            PrintWriter printWriter = new PrintWriter(baos, true);
            HtmlTablePrinter htmlTablePrinter = new HtmlTablePrinter(printWriter, ConvertorToStringForLiterature.getTable(shelfLiteratureObjects), isNeedIndex, isForEditing);
            htmlTablePrinter.setTitleList(Arrays.asList(ItemTitles.NAME, ItemTitles.PAGES, ItemTitles.BORROWED, ItemTitles.AUTHOR,  ItemTitles.DATE, ItemTitles.PUBLISHER));
            htmlTablePrinter.print();
            return baos.toString();
        }
    }
}
