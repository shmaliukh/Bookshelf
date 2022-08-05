package org.vshmaliukh.console_terminal.services;

import org.vshmaliukh.bookshelf.literature_items.Item;

import java.util.*;
import java.util.stream.Collectors;

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

    public static String generateHTMLFormItem(String value, String defaultValue) {
        return "" +
                "       <br>\n" +
                "Enter " + value + "\n" +
                "       <br>\n" +
                "<input type = \"text\" name = \"" + value + "\" value=\"" + defaultValue + "\">\n" +
                "       <br>\n";
    }

    public static String generateHTMLFormItem(String value) {
        return "" +
                "       <br>\n" +
                "Enter " + value + "\n" +
                "       <br>\n" +
                "<input type = \"text\" name = \"" + value + "\">\n" +
                "       <br>\n";
    }

    public static String generateHTMLFormItems(String... values) {
        return generateHTMLFormItems(Arrays.asList(values));
    }

    public static String generateHTMLFormItems(Collection<String> values) {
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            //sb.append(generateHTMLFormItem(value));
        }
        return sb.toString();

    }
}
