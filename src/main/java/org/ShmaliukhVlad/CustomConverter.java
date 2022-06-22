package org.ShmaliukhVlad;

import com.google.gson.*;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;

import java.lang.reflect.Type;

public class CustomConverter implements JsonSerializer<Literature>, JsonDeserializer<Literature> {
    @Override
    public JsonElement serialize(Literature literature,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        JsonObject object = new JsonObject();

        object.addProperty("name", literature.getName());
        object.addProperty("pagesNumber", literature.getPagesNumber());
        object.addProperty("isBorrowed", literature.isBorrowed());

        if(literature instanceof Book){
            object.addProperty("author", ((Book) literature).getAuthor());
            object.addProperty("issuanceDate", ((Book) literature).getIssuanceDate());
        }
        return object;
    }

    @Override
    public Literature deserialize(JsonElement jsonElement,
                                  Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {


        //Todo
        return null;
    }


}
