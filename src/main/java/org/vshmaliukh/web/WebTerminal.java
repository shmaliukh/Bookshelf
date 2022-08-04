package org.vshmaliukh.web;

import org.vshmaliukh.terminal.Terminal;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.Shelf;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.services.print_table_service.ConvertorToStringForLiterature;
import org.vshmaliukh.terminal.services.print_table_service.TablePrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class WebTerminal extends Terminal {

    public WebTerminal(String userName, int typeOfWorkWithFiles) {
        this.user = new User(userName);
        this.shelf = new Shelf(null); // TODO
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;

        setUpGsonHandler();
        readShelfItemsFromJson();
    }


    public String generateTableOfShelf() {
        List<Item> shelfAllLiteratureObjects = shelf.getAllLiteratureObjects();
        if (shelfAllLiteratureObjects.isEmpty()) {
             return "No available literature of Shelf to print";
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(baos, true);
            TablePrinter.printHTMLTable(printWriter, ConvertorToStringForLiterature.getTable(shelfAllLiteratureObjects), false);
            return baos.toString();
        }
    }
}
