package org.vshmaliukh.services.print_table_service;

import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;

import java.util.Date;
import java.util.List;

public class ConvertorToStringForLiterature implements ConvertorToString<Literature> {
    @Override
    public List<String> convertObjectToListOfString(Literature obj) {
        return null;
    }


    //public static Class initClazz(String simpleName) throws ClassNotFoundException{
    //        return Class.forName(Literature.class.getPackage().getName() + "." + simpleName);
    //}
    //
    //@Override
    //public List<String> convertObjectToListOfString(Literature obj) {
    //    return null;
    //}
    //
    //public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    //
    //    Literature literature = new Book("asd", 12, true, "asdaddasd", new Date());
    //    Literature literature1 = Class.forName(Book.class.getSimpleName()).newInstance();
    //
    //}
}
