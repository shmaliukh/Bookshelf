package org.vshmaliukh;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
    public static final String TYPE_OF_WORK_WITH_FILES = "typeOfWork";
    public static final String USER_NAME = "userName";
    public static final List<String> USER_PARAMETER_LIST = Collections.unmodifiableList(Arrays.asList(USER_NAME, TYPE_OF_WORK_WITH_FILES));

    public static final String LOG_IN_TITLE = "log_in";
    public static final String MAIN_MENU_TITLE = "main_menu";
    public static final String ADD_MENU_TITLE = "add_menu";
    public static final String SORTING_TYPES_MENU_TITLE = "sorting_types_menu";
    public static final String ITEMS_SORTING_MENU_TITLE = "items_sorting_menu";
    public static final String ADD_ITEM_TITLE = "add_item";
    public static final String DELETE_ITEM_TITLE = "delete_item";
    public static final String EDIT_ITEMS_TITLE = "edit_items";
    public static final String CHANGE_ITEM_BORROWED_STATE = "change_item_borrowed_state";
    public static final String INDEX_OF_ITEM = "indexOfItem";
    public static final String ITEM_CLASS_TYPE = "itemClassType";
    public static final String ITEM_GSON_STR = "itemGsonStr";
    public static final String IS_RANDOM = "isRandom";
    public static final String MENU_ITEM_INDEX = "menuItemIndex";
    public static final String INFORM_MESSAGE = "inform_message";
}