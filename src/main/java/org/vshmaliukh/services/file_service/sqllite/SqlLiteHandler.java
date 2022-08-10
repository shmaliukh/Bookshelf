package org.vshmaliukh.services.file_service.sqllite;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.console_terminal_app.PlainTextTableHandler;
import org.vshmaliukh.services.file_service.SaveReadUserFilesHandler;
import org.vshmaliukh.services.print_table_service.ConvertorToStringForItems;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.TITLE_LIST;

@Slf4j
public class SqlLiteHandler extends SaveReadUserFilesHandler {

    public static final String SQL_FILE_TYPE = ".db";
    private final String dbPathStr;

    public SqlLiteHandler(String homeDir, String userName) {
        super(homeDir, userName);

        dbPathStr = "jdbc:sqlite:" + Paths.get(generatePathForFileHandler().toString(), generateFullFileName());
        generateTablesIfNotExists(); // TODO
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
        String sqlLiteHandlerFolderStr = "sqlLite_handler";
        Path path = Paths.get(String.valueOf(generatePathForUser()), sqlLiteHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    public String getDbPathStr() {
        return dbPathStr;
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        for (Item item : listToSave) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(item.getClass());
            String sqlInsertStr = handlerByClass.generateSqlInsertStr(item);
            List<String> parameterList = handlerByClass.parameterList();
            Map<String, String> convertItemToListOfString = handlerByClass.convertItemToListOfString(item);
            SqlLiteUtils.insert(dbPathStr, sqlInsertStr, parameterList, convertItemToListOfString);
        }
    }

    private void generateTablesIfNotExists() {
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String tableStr = handlerByClass.generateSqlTableStr(classType);
            SqlLiteUtils.createNewTable(dbPathStr, tableStr);
        }
    }

    @Override
    public List<Item> readItemList() {
        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> classType : ItemHandlerProvider.uniqueTypeNames) {
            ItemHandler handlerByClass = ItemHandlerProvider.getHandlerByClass(classType);
            String sqlSelectAll = handlerByClass.generateSqlSelectAllParametersByClass(classType);
            List<Map<String,String>> itemMaps = SqlLiteUtils.selectAllByClass(dbPathStr,sqlSelectAll, handlerByClass.parameterList());
            for (Map<String, String> itemMap : itemMaps) {
                Item item = handlerByClass.generateItemByParameterValueMap(itemMap);
                itemList.add(item);
            }
        }
        return itemList;
    }

    public static void main(String[] args) {
        Random random = new Random();
        SqlLiteHandler sqlLiteHandler = new SqlLiteHandler(System.getProperty("user.home"), "sql_test");
        SqlLiteUtils.createNewDatabase(sqlLiteHandler.getDbPathStr());

        List<Item> itemList = new ArrayList<>();
        for (Class<? extends Item> uniqueTypeName : ItemHandlerProvider.uniqueTypeNames) {
            itemList.add(ItemHandlerProvider.getHandlerByClass(uniqueTypeName).getRandomItem(random));
            itemList.add(ItemHandlerProvider.getHandlerByClass(uniqueTypeName).getRandomItem(random));
        }

        //System.out.println(itemList);
        //System.out.println();
        //System.out.println(sqlLiteHandler.readItemList());
        new PlainTextTableHandler(new PrintWriter(System.out), ConvertorToStringForItems.getTable(itemList), true).print();
    }
}

