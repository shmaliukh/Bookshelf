package org.vshmaliukh.shelf.literature_items;

import java.util.Map;
import java.util.Random;

public interface ItemWebIntegrationInterface<T extends Item> {

    String generateHTMLFormBodyToCreateItem();

    String generateHTMLFormBodyToCreateItem(Random random);

    boolean isValidHTMLFormData(Map<String, String> mapFieldValue);

    T generateItemByParameterValueMap(Map<String, String> mapFieldValue);

}
