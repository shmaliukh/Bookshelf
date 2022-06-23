package org.ShmaliukhVlad.bookshelf;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class DateSerializer implements JsonSerializer<LocalDate> {
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(
                new StringBuilder(
                        src.getYear())
                        .append("-")
                        .append(src.getMonthValue())
                        .append("-")
                        .append(src.getDayOfMonth())
                        .toString()
        );
    }
}
