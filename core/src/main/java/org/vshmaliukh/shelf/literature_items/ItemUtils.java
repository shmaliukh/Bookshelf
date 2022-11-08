package org.vshmaliukh.shelf.literature_items;

import java.util.*;
import java.util.stream.Collectors;

public final class ItemUtils {

    public static final String FRIENDLY_TRUE_STR_VALUE = "yes";
    public static final String FRIENDLY_FALSE_STR_VALUE = "no";

    private ItemUtils() {
    }

    public static String convertBorrowed(boolean isBorrowed) {
        if (isBorrowed) {
            return FRIENDLY_TRUE_STR_VALUE;
        }
        return FRIENDLY_FALSE_STR_VALUE;
    }

    public static String getRandomString(int length, Random random) {
        String charactersForRandomStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_      ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int characterPosition = random.nextInt(charactersForRandomStr.length());
            sb.append(charactersForRandomStr.charAt(characterPosition));
        }
        return sb.toString();
    }

    public static <T extends Item> List<T> getItemsByType(Class<T> clazz, List<? extends Item> items) {
        if (clazz == null || items == null) {
            return Collections.emptyList();
        }
        return items.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public static <T extends Item> List<T> getSortedLiterature(List<T> literatureList, Comparator<T> itemsComparator) {
        if (literatureList == null || itemsComparator == null) {
            return Collections.emptyList();
        }
        return literatureList.stream()
                .sorted(itemsComparator)
                .collect(Collectors.toList());
    }

    public static String generateHTMLFormItem(String value, String inputType, String defaultValue) {
        return "" +
                "       <br>\n" +
                "Enter " + value + "\n" +
                "       <br>\n" +
                "<input type = \"" + inputType + "\" " +
                "       name = \"" + value + "\" " +
                "       value= \"" + defaultValue + "\">\n" +
                "       <br>\n";
    }

    public static String generateHTMLFormItem(String value, String inputType) {
        return "" +
                "       <br>\n" +
                "Enter " + value + "\n" +
                "       <br>\n" +
                "<input type = \"" + inputType + "\" " +
                "       name = \"" + value + "\">\n" +
                "       <br>\n";
    }

    public static String generateHTMLFormRadio(String value) {
        return "" +
                "       <br>\n" +
                value +
                "       <br>\n" +
                "<input type=\"radio\" " +
                "       id=\"true\"\n" +
                "       name=\"" + value + "\"" +
                "       value=\"y\">\n" +
                "<label for=\"true\"> " + FRIENDLY_TRUE_STR_VALUE + " <label/>\n" +
                "       <br>\n" +
                "<input type=\"radio\" " +
                "       id=\"false\"\n" +
                "       name=\"" + value + "\"" +
                "       value=\"n\" checked>\n" +
                "<label for=\"false\"> " + FRIENDLY_FALSE_STR_VALUE + " <label/>\n" +
                "       <br>\n";
    }
}
