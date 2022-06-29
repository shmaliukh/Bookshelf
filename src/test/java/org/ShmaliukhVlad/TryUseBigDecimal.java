package org.ShmaliukhVlad;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.Collection;

public class TryUseBigDecimal {

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5};

        System.out.println(getLastElementValue(array));
    }

    public static int getLastElementValue(int[] array) {
        return array[-3];
    }

}
