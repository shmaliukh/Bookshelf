package org.ShmaliukhVlad.bookshelf;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;

import java.lang.reflect.Type;

public class BookSerializer implements JsonSerializer<Book>
{
    @Override
    public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();

        result.addProperty("Name", src.getName());
        result.addProperty("Pages", src.getPagesNumber());
        result.addProperty("Borrowed", src.isBorrowed());
        result.addProperty("Author", src.getAuthor());
        result.add("Date", context.serialize(src.getIssuanceDate()));

        return result;
    }
}
