package org.vshmaliukh.terminal.services;

import org.vshmaliukh.terminal.bookshelf.literature_items.Item;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles.NAME;
import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles.PAGES;

public class Utils {

    private Utils() {
    }

    public static String getRandomString(int length, Random random) {
        String charactersForRandomStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int characterPosition = random.nextInt(charactersForRandomStr.length());
            sb.append(charactersForRandomStr.charAt(characterPosition));
        }
        return sb.toString();
    }

    public static <T> List<T> getItemsByType(Class<T> clazz, List<?> items) {
        return items.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public static <T extends Item> List<T> getSortedLiterature(List<T> literatureList, Comparator<T> itemsComparator) {
        return literatureList.stream()
                .sorted(itemsComparator)
                .collect(Collectors.toList());
    }

    public static String generateHTMLFormItem(String value) {
        return "" +
                "       <br>\n" +
                "Enter " + value + "\n"+
                "       <br>\n" +
                "<input type = \"text\" " + value + "=\"" + value + "\" id=\"" + value + "\">\n "+
                "       <br>\n";

    }
}
