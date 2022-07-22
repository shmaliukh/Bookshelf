package org.vshmaliukh;

import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Method which gives opportunity to get string with random characters ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ")
     *
     * @param length       is value of expected string size
     * @param random
     * @return string with random symbols
     */
    public static String getRandomString(int length, Random random) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    public static <T> List<T> getItemsByType(Class<T> clazz, List<?> items) {
        return items.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

}
