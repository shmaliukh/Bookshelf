package org.ShmaliukhVlad;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.serices.ShelfContainer;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class TerminalTest {
    @Test
    void test() throws IOException {
        Magazine m = new Magazine("asd",15, false);
        Book b = new Book("name", 100, false, "author", new Date());
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(b);
        shelf.addLiteratureObject(m);

        Path path = Paths.get("some.json");
        Gson gson = new Gson();

        Container container = new Container();
        container.generateArr(shelf);
        //container.generateMap(shelf);

        ArrayList<Item> arr = new ArrayList<>();
        arr.addAll(shelf.getLiteratureInShelf())

        FileWriter fw = new FileWriter(String.valueOf(path));
        gson.toJson(shelf.getBooks().s,fw);
        gson.toJson(shelf.getMagazines(),fw);
        fw.flush();
        fw.close();

        Shelf shelf1 = new Shelf();
        Item i = new Gson().fromJson(Files.newBufferedReader(path,StandardCharsets.UTF_8), Item.class);

        //for (Map.Entry<Class, Object> objectEntry : x.getMap().entrySet()){
        //    shelf1.addLiteratureObject((Literature) objectEntry.getValue());
        //}
        for (Item item:
             container.getArrayList()) {
            if(item.object instanceof Book){
                shelf1.addLiteratureObject( (Book) item.object);
            }
            else if(item.object instanceof Magazine){
                shelf1.addLiteratureObject( (Magazine) item.object);
            }
        }

        System.out.println(shelf1);


        //FileReader fr = new FileReader("some.json");



        //System.out.println(fr.read());

    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Item{
    Class type;
    Object object;
}

@Data
@NoArgsConstructor
class Container{
    List<Item> arrayList = new ArrayList<>();

    public void generateArr(Shelf shelf){
        for (Book b:
             shelf.getBooks()) {
            arrayList.add(new Item(b.getClass(), (Object) b));
        }
        for (Magazine m:
             shelf.getMagazines()) {
            arrayList.add(new Item(Magazine.class, m));
        }
    }

    //Map<Class, Object> map = new HashMap<>();
    //
    //public void generateMap(Shelf shelf){
    //    for (Book b : shelf.getBooks()) {
    //        map.put(b.getClass(),b);
    //    }
    //    for (Magazine m : shelf.getMagazines()) {
    //        map.put(m.getClass(),m);
    //    }
    //}

}
    ///**
    // * Special capture variables for the capture console output
    // */
    //ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //PrintStream ps = new PrintStream(baos);
    //PrintStream old = System.out;
//
    //@Test
    //@DisplayName("test state of terminal start")
    //void startWork() {
    //    String expectedString =
    //                    "\n" +
    //                    "Enter number of  command you wand to execute:" + "\n" +
    //                    "1 - Add new Literature object to Shelf" + "\n" +
    //                    "2 - Delete  Literature object by index from Shelf" + "\n" +
    //                    "3 - Borrow  Literature object by index from Shelf" + "\n" +
    //                    "4 - Arrive  Literature object by index back to Shelf" + "\n" +
    //                    "5 - Print list of available Books sorted by parameter..." + "\n" +
    //                    "6 - Print list of available Magazines sorted by parameter..." + "\n" +
    //                    "7 - Save in file" + "\n" +
    //                    "8 - Deserialize" + "\n" +
    //                    "9 - Print current state of Shelf" + "\n" +
    //                    "0 - Exit";
//
    //    Terminal terminal = new Terminal();
    //    terminal.startWork();
//
    //    System.setOut(ps);
    //    System.out.flush();
    //    System.setOut(old);
//
    //    terminal.setPlay(false);
//
    //    assertEquals(expectedString, baos.toString().trim());
//
    //}
//
    //@Test
    //@DisplayName("test getRandomString")
    //@Description("generate string with expected length")
    //void getRandomString() {
    //    int expectedLength = new Random().nextInt(10);
    //    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
    //    Random random = new Random();
    //    StringBuilder sb = new StringBuilder();
    //    for (int i = 0; i < expectedLength; i++) {
    //        int number = random.nextInt(62);
    //        sb.append(str.charAt(number));
    //    }
////
    //    assertEquals(sb.toString().length(), expectedLength);
    //}
//}