package org.ShmaliukhVlad;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestJsonWriteAndRead {

    @Test
    public void test() throws IOException {
        List<MyBook> bookList = new ArrayList<>();
        bookList.add(new MyBook("name", "author", new Date(), 10));
        List<MyMagazine> magazineList = new ArrayList<>();
        magazineList.add(new MyMagazine("name", 10));
        Container container = new Container();
        container.bookList = bookList;
        container.magazineList = magazineList;

        Gson gson = new Gson();
        FileWriter writer = new FileWriter("test.json");
        gson.toJson(container, writer);
        writer.flush();
        writer.close();

        bookList.get(0).getDate().setTime(System.currentTimeMillis());


//        Container x = gson.fromJson(new FileReader("test.json"), Container.class);
        JsonElement x = gson.fromJson(new FileReader("test.json"), JsonElement.class);
        System.out.println(x);
        for (JsonElement jsonElement : x.getAsJsonArray()) {
            JsonElement type = jsonElement.getAsJsonObject().get("type");
            if(type.equals("book")){
                gson.fromJson(jsonElement, Book.class);
            }
        }
        final Date d = new Date();
        System.out.println(d);


    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyBook {
        private String name;
        private String author;
        private Date date;
        private int pages;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyMagazine {
        private String name;
        private int pages;
    }

    @ToString
    public static class Container {
        public List<MyBook> bookList;
        public List<MyMagazine> magazineList;
    }
}
