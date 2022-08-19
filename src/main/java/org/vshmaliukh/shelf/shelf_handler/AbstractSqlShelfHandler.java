package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.console_terminal_app.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSqlShelfHandler extends SaveReadShelfHandler {

    @Override
    public List<Item> readLiteratureInShelf() {
        return getShelf().itemsOfShelf.stream() // todo
                .filter(o -> !o.isBorrowed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> readLiteratureOutShelf() {
        return getShelf().itemsOfShelf.stream()
                .filter(Item::isBorrowed)
                .collect(Collectors.toList());
    }

    @Override
    public void setUpDataSaver(String userName, int typeOfWorkWithFiles) {
        // TODO
    }
}
