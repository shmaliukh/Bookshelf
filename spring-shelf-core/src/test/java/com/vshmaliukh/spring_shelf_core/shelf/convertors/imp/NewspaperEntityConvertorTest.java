package com.vshmaliukh.spring_shelf_core.shelf.convertors.imp;

import com.vshmaliukh.spring_shelf_core.shelf.entities.NewspaperEntity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewspaperEntityConvertorTest {

    static Newspaper newspaper1 = new Newspaper("someName1", 1, false);
    static Newspaper newspaper2 = new Newspaper("someName2", 2, true);
    static NewspaperEntity newspaperEntity1 = new NewspaperEntity();
    static NewspaperEntity newspaperEntity2 = new NewspaperEntity();

    static {
        newspaperEntity1.setId(null);
        newspaperEntity1.setUserId(1);
        newspaperEntity1.setName(newspaper1.getName());
        newspaperEntity1.setPages(newspaper1.getPagesNumber());
        newspaperEntity1.setBorrowed(newspaper1.isBorrowed());

        newspaperEntity2.setId(null);
        newspaperEntity2.setUserId(1);
        newspaperEntity2.setName(newspaper2.getName());
        newspaperEntity2.setPages(newspaper2.getPagesNumber());
        newspaperEntity2.setBorrowed(newspaper2.isBorrowed());
    }

    static NewspaperEntityConvertor convertor = new NewspaperEntityConvertor();

    private static Stream<Arguments> providedArgsToConvert() {
        return Stream.of(
                Arguments.of(newspaperEntity1, newspaper1),
                Arguments.of(newspaperEntity2, newspaper2)
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedItemFromEntity(NewspaperEntity toConvert, Newspaper expectedItem) {
        Newspaper itemFromEntity = convertor.getConvertedItemFromEntity(toConvert);
        assertEquals(expectedItem.getName(), toConvert.getName());
        assertEquals(expectedItem.getPagesNumber(), toConvert.getPages());
        assertEquals(expectedItem.isBorrowed(), toConvert.isBorrowed());
        assertEquals(expectedItem.toString(), itemFromEntity.toString());
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedEntityFromItem(NewspaperEntity expectedEntity, Newspaper toConvert) {
        NewspaperEntity entityFromItem = convertor.getConvertedEntityFromItem(toConvert, null);
        assertEquals(expectedEntity.getId(), entityFromItem.getId());
        assertEquals(expectedEntity.getName(), entityFromItem.getName());
        assertEquals(expectedEntity.getPages(), entityFromItem.getPages());
        assertEquals(expectedEntity.isBorrowed(), entityFromItem.isBorrowed());
    }

}