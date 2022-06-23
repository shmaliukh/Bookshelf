package org.ShmaliukhVlad.bookshelf;

import com.google.gson.*;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class BookGsonService implements JsonSerializer<Book>, JsonDeserializer<Book>
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

    @Override
    public Book deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name;
        int pagesNumber;
        boolean isBorrowed;
        String author;
        LocalDate dateOfIssue;

        name = jsonObject.get("Name").getAsString();
        pagesNumber = jsonObject.get("Pages").getAsInt();
        isBorrowed = jsonObject.get("Borrowed").getAsBoolean();
        author = jsonObject.get("Author").getAsString();
        dateOfIssue = jsonDeserializationContext.deserialize(jsonObject.get("Date"), LocalDate.class);

        return new Book(name, pagesNumber, isBorrowed, author, dateOfIssue);
    }
}
