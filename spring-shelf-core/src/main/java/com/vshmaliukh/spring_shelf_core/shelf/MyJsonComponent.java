//package com.vshmaliukh.spring_shelf_core.shelf;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.jackson.JsonComponent;
//import org.vshmaliukh.shelf.literature_items.Item;
//
//import java.io.IOException;
//
//@Slf4j
//@JsonComponent
//public final class MyJsonComponent extends JsonSerializer<Item> {
//
//    @Override
//    public void serialize(Item item, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
//        Class<? extends Item> itemClass = item.getClass();
//
//        try {
//            jsonGenerator.writeStartObject();
//            jsonGenerator.writeStringField("classType", String.valueOf(itemClass)); // ???
//            jsonGenerator.writeEndObject();
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//}
