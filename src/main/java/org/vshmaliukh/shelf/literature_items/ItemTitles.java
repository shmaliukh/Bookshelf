package org.vshmaliukh.shelf.literature_items;

import java.util.Arrays;
import java.util.List;

public class ItemTitles {

    private ItemTitles() {
    }

    public static final String TYPE = "TYPE";
    public static final String NAME = "NAME";
    public static final String PAGES = "PAGES";
    public static final String BORROWED = "BORROWED";
    public static final String AUTHOR = "AUTHOR";
    public static final String PUBLISHER = "PUBLISHER";
    public static final String DATE = "DATE";

    public static final List<String> TITLE_LIST = Arrays.asList(TYPE, NAME, PAGES, BORROWED, AUTHOR, DATE, PUBLISHER);
}
