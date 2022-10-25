package com.vshmaliukh.spring_shelf_core.shelf.convertors.imp;

import com.vshmaliukh.spring_shelf_core.shelf.entities.MagazineEntity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagazineEntityConvertorTest {

    static Magazine magazine1 = new Magazine("noNameBook1", 1, false);
    static Magazine magazine2 = new Magazine("noNameBook2", 2, true);
    static MagazineEntity magazineEntity1 = new MagazineEntity();
    static MagazineEntity magazineEntity2 = new MagazineEntity();

    static {
        magazineEntity1.setId(null);
        magazineEntity1.setUserId(1);
        magazineEntity1.setName(magazine1.getName());
        magazineEntity1.setPages(magazine1.getPagesNumber());
        magazineEntity1.setBorrowed(magazine1.isBorrowed());

        magazineEntity2.setId(null);
        magazineEntity2.setUserId(1);
        magazineEntity2.setName(magazine2.getName());
        magazineEntity2.setPages(magazine2.getPagesNumber());
        magazineEntity2.setBorrowed(magazine2.isBorrowed());
    }

    static MagazineEntityConvertor convertor = new MagazineEntityConvertor();

    private static Stream<Arguments> providedArgsToConvert() {
        return Stream.of(
                Arguments.of(magazineEntity1, magazine1),
                Arguments.of(magazineEntity2, magazine2)
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedItemFromEntity(MagazineEntity toConvert, Magazine expectedItem) {
        Magazine itemFromEntity = convertor.getConvertedItemFromEntity(toConvert);
        assertEquals(expectedItem.getName(), toConvert.getName());
        assertEquals(expectedItem.getPagesNumber(), toConvert.getPages());
        assertEquals(expectedItem.isBorrowed(), toConvert.isBorrowed());
        assertEquals(expectedItem.toString(), itemFromEntity.toString());
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedEntityFromItem(MagazineEntity expectedEntity, Magazine toConvert) {
        MagazineEntity entityFromItem = convertor.getConvertedEntityFromItem(toConvert, null);
        assertEquals(expectedEntity.getId(), entityFromItem.getId());
        assertEquals(expectedEntity.getName(), entityFromItem.getName());
        assertEquals(expectedEntity.getPages(), entityFromItem.getPages());
        assertEquals(expectedEntity.isBorrowed(), entityFromItem.isBorrowed());
    }

}