package org.ShmaliukhVlad.bookshelf;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DateGsonService implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
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

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        String data = jsonElement.getAsString();
        List<String> parts = Arrays.asList(data.split("-"));

        int year = Integer.parseInt(parts.get(0));
        int month = Integer.parseInt(parts.get(1));
        int day = Integer.parseInt(parts.get(2));

        return LocalDate.of(year, month, day);
    }
}
