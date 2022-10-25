package com.vshmaliukh.spring_shelf_core.shelf.convertors.imp;

import com.vshmaliukh.spring_shelf_core.shelf.entities.ComicsEntity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComicsEntityConvertorTest {

    static Comics comics1 = new Comics("someName1", 1, false, "somePublisher");
    static Comics comics2 = new Comics("someName2", 2, true, "somePublisher");
    static ComicsEntity comicsEntity1 = new ComicsEntity();
    static ComicsEntity comicsEntity2 = new ComicsEntity();

    static {
        comicsEntity1.setId(null);
        comicsEntity1.setUserId(1);
        comicsEntity1.setName(comics1.getName());
        comicsEntity1.setPages(comics1.getPagesNumber());
        comicsEntity1.setBorrowed(comics1.isBorrowed());

        comicsEntity2.setId(null);
        comicsEntity2.setUserId(1);
        comicsEntity2.setName(comics2.getName());
        comicsEntity2.setPages(comics2.getPagesNumber());
        comicsEntity2.setBorrowed(comics2.isBorrowed());
    }

    static ComicsEntityConvertor convertor = new ComicsEntityConvertor();

    private static Stream<Arguments> providedArgsToConvert() {
        return Stream.of(
                Arguments.of(comicsEntity1, comics1),
                Arguments.of(comicsEntity2, comics2)
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedItemFromEntity(ComicsEntity toConvert, Comics expectedItem) {
        Comics itemFromEntity = convertor.getConvertedItemFromEntity(toConvert);
        assertEquals(expectedItem.getName(), toConvert.getName());
        assertEquals(expectedItem.getPagesNumber(), toConvert.getPages());
        assertEquals(expectedItem.isBorrowed(), toConvert.isBorrowed());
        assertEquals(expectedItem.toString(), itemFromEntity.toString());
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedEntityFromItem(ComicsEntity expectedEntity, Comics toConvert) {
        ComicsEntity entityFromItem = convertor.getConvertedEntityFromItem(toConvert, null);
        assertEquals(expectedEntity.getId(), entityFromItem.getId());
        assertEquals(expectedEntity.getName(), entityFromItem.getName());
        assertEquals(expectedEntity.getPages(), entityFromItem.getPages());
        assertEquals(expectedEntity.isBorrowed(), entityFromItem.isBorrowed());
    }

}