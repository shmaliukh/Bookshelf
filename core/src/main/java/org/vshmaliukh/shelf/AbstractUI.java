package org.vshmaliukh.shelf;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.shelf_handler.User;

import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractUI {

    public static final int WRONG_INPUT = -1;
    public static final String ENTER_ANOTHER_VALUE_TO_RETURN = "Enter another value to return";
    public static final String CHOOSE_TYPE_OF_SORTING = "Choose type of sorting:";
    public static final String NO_AVAILABLE_LITERATURE_ITEM_IN_SHELF_FOR_SORTING = "No available literature item IN shelf for sorting";

    protected Random random = new Random();

    protected User user;
    protected int saveReadServiceType;
    protected SaveReadShelfHandler shelfHandler;

    public abstract void configShelfHandler();

    public SaveReadShelfHandler getShelfHandler() {
        return shelfHandler;
    }
}

