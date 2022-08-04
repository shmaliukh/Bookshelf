package org.vshmaliukh.terminal;

import lombok.Data;
import org.vshmaliukh.terminal.User;
import org.vshmaliukh.terminal.bookshelf.Shelf;
import org.vshmaliukh.terminal.services.gson_service.ItemGsonHandler;
import org.vshmaliukh.terminal.services.gson_service.ItemGsonHandlerOneFile;
import org.vshmaliukh.terminal.services.gson_service.ItemGsonHandlerPerType;

import java.io.PrintWriter;
import java.util.Random;
@Data
public abstract class Terminal {

    public static final String DATE_FORMAT_STR = "dd-MM-yyyy";

    public static final int FILE_MODE_WORK_WITH_ONE_FILE = 1;
    public static final int FILE_MODE_WORK_WITH_FILE_PER_TYPE = 2;

    public static final int WRONG_INPUT = -1;

    public static final  String HOME_PROPERTY = System.getProperty("user.home");

    protected Random random;
    protected ItemGsonHandler itemGsonHandler;

    protected Shelf shelf;
    protected PrintWriter printWriter;
    protected User user;
    protected int typeOfWorkWithFiles;

    public void setUpGsonHandler() {
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                itemGsonHandler = new ItemGsonHandlerOneFile(HOME_PROPERTY, user.getName());
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                itemGsonHandler = new ItemGsonHandlerPerType(HOME_PROPERTY, user.getName());
                break;
            default:
                itemGsonHandler = new ItemGsonHandlerOneFile(HOME_PROPERTY, user.getName());
                break;
        }
    }

    public void saveShelfItemsToJson() {
        itemGsonHandler.saveToFile(shelf.getAllLiteratureObjects());
    }

    public void readShelfItemsFromJson() {
        itemGsonHandler.readListFromFile().forEach(shelf::addLiteratureObject);
    }

    public void informAboutFileTypeWork(int typeOfWorkWithFiles) {
        printWriter.println("Type of work with save/read shelf with files: ");
        switch (typeOfWorkWithFiles) {
            case FILE_MODE_WORK_WITH_ONE_FILE:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
                break;
            case FILE_MODE_WORK_WITH_FILE_PER_TYPE:
                printWriter.println("FILE_MODE_WORK_WITH_FILE_PER_TYPE");
                break;
            default:
                printWriter.println("FILE_MODE_WORK_WITH_ONE_FILE");
                break;
        }
    }

    public void printCurrentStateOfShelf(){}

}
