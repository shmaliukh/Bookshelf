package org.ShmaliukhVlad;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;

import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.ShmaliukhVlad.bookshelf.Shelf.readShelfFromGsonFile;

class ContainerNewTest {
    @Test
    void readFileTest() throws IOException, ClassNotFoundException {
        FileReader fr = new FileReader("src/test/resources/testGsonReader.json");
        String strGsonFile = fr.toString();

        String fineName = "readFileTest.json";
        FileWriter fw = new FileWriter(fineName);

        fw.write(strGsonFile);
        fw.flush();
        fw.close();

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("book name", 123,true, "author" ,new Date()));
        shelf.addLiteratureObject(new Magazine("magazine name", 123,false));

        shelf = readShelfFromGsonFile(fineName);


    }

    @Test
    void test() throws IOException {
        FileWriter fw =new FileWriter("newGsonContainer.json");
        FileReader fr =new FileReader("newGsonContainer.json");

        Magazine m = new Magazine("asd",15, false);
        Book b = new Book("name", 100, false, "author", new Date());
        Shelf expectedShelf = new Shelf();
        expectedShelf.addLiteratureObject(b);
        expectedShelf.addLiteratureObject(m);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Container c1 = new Container(b);
        Container c2 = new Container(m);
        List<Container> containerArrayList= new ArrayList<>();
        containerArrayList.add(c1);
        containerArrayList.add(c2);

        gson.toJson(containerArrayList,fw);
        fw.flush();
        fw.close();


        List<Literature> literatureList = new ArrayList();
        JsonArray jsonArray = gson.fromJson(fr, JsonArray.class);
        for (JsonElement element : jsonArray) {
            System.out.println("element: " + element);
            JsonObject itemObject = element.getAsJsonObject().getAsJsonObject("Literature");
            String typeOfClass = element.getAsJsonObject().get("ClassType").getAsString();
            System.out.println("    classType: " + typeOfClass);
            System.out.println("    itemObject: " + itemObject);


            List<Book> books = new ArrayList<>();
            List<Magazine> magazines = new ArrayList<>();

            if (typeOfClass.equals(Book.class.toString())) {
                books.add(gson.fromJson(itemObject, Book.class));
                System.out.println("        book: " + gson.fromJson(itemObject, Book.class));
            }
            else if (typeOfClass.equals(Magazine.class.toString())) {
                magazines.add(gson.fromJson(itemObject, Magazine.class));
                System.out.println("        magazine: " + gson.fromJson(itemObject, Magazine.class));
            }
            literatureList.addAll(books);
            literatureList.addAll(magazines);
        }

        Shelf shelf = new Shelf(literatureList);
        System.out.println(shelf);
    }
}

@Data
@NoArgsConstructor
class Container{
    @SerializedName("ClassType")
    String classOfLiterature;
    @SerializedName("Literature")
    Object literature;

    public Container(String classOfLiterature, Object literature){
        this.classOfLiterature = classOfLiterature;
        this.literature = literature;
    }

    public Container(Object literature){
        this.classOfLiterature = literature.getClass().toString();
        this.literature = literature;
    }
}

