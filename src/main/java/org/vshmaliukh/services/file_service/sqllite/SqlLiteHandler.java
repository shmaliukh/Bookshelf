package org.vshmaliukh.services.file_service.sqllite;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.file_service.SaveReadUserFilesHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
public class SqlLiteHandler extends SaveReadUserFilesHandler {

    public static final String SQL_FILE_TYPE = ".db";
    private final String sqlLiteHandlerFolderStr = "sqlLite_handler";
    private final String dbPathStr;

    public SqlLiteHandler(String homeDir, String userName) {
        super(homeDir, userName);
        dbPathStr = "jdbc:sqlite:" + Paths.get(generatePathForFileHandler().toString(), generateFullFileName());
    }

    public static void logQslLiteErr(Exception e) {
        log.error("[SqlLite_handler] got err. Exception: ", e);
    }

    @Override
    public String generateFullFileName() {
        return userName + "_sqllite_db" + SQL_FILE_TYPE;
    }

    @Override
    public Path generatePathForFileHandler() {
        Path path = Paths.get(String.valueOf(generatePathForUser()), sqlLiteHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    public String getDbPathStr() {
        return dbPathStr;
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        generateTablesIfNotExists();

        for (Item item : listToSave) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String sqlInsertStr = handlerByClass.generateSqlInsertStr(item);
            SqlLiteUtils.insert(dbPathStr, sqlInsertStr);
        }
    }

    private void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.generateSqlTableStr();
            SqlLiteUtils.createNewTable(dbPathStr, tableStr);
        }
    }

    @Override
    public List<Item> readItemList() {
        return null;
    }

    public static void main(String[] args) {
        SqlLiteHandler sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), "sql_test");
        SqlLiteUtils.createNewDatabase(sqlLiteHandler.getDbPathStr());

        Book book = new Book("noNameBook2",2,false,"NoAuthor2",new Date());
        List<Item> itemList = Collections.singletonList(book);
        sqlLiteHandler.saveItemList(itemList);
    }
}

