//package org.ShmaliukhVlad;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import org.ShmaliukhVlad.bookshelf.Shelf;
//import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
//
//import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
//import org.junit.jupiter.api.Test;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.*;
//
//
//class ContainerNewTest {
//    @Test
//    void test() throws IOException {
//        FileWriter fw =new FileWriter("some.json");
//        FileReader fr =new FileReader("some.json");
//
//        Magazine m = new Magazine("asd",15, false);
//        Book b = new Book("name", 100, false, "author", new Date());
//        Shelf expectedShelf = new Shelf();
//        expectedShelf.addLiteratureObject(b);
//        expectedShelf.addLiteratureObject(m);
//
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//
//        //AnotherContainer anotherContainer = new AnotherContainer();
//        //anotherContainer.getList().add(new Container(b.getClass().toString(),b));
//        //anotherContainer.getList().add(new Container(m.getClass().toString(),m));
////
//        //gson.toJson(anotherContainer,fw);
////
//        //Type type = new TypeToken<AnotherContainer>(){}.getType();
//        //AnotherContainer anotherContainer1 = gson.fromJson(fr, AnotherContainer.class);
//        //fw.flush();
//        //fw.close();
////
//        //System.out.println(anotherContainer1.getList().get(0));
//
////
//        //System.out.println(expectedShelf.getAllLiterature());
//
//
//        //List<Container> arrLiterature = new ArrayList<>();
//        //for (Magazine magazine: expectedShelf.getMagazines()) {
//        //    arrLiterature.add(new Container( magazine));
//        //}
//        //for (Book book: expectedShelf.getBooks()) {
//        //    arrLiterature.add(new Container( book));
//        //}
//        //System.out.println(arrLiterature);
//
//        //List<Container> arrContainer = new ArrayList<>();
//        //gson.toJson(arrLiterature,fw);
//
//
//        //for (Magazine magazine: expectedShelf.getMagazines()) {
//        //    gson.toJson(new Container(magazine), fw);
//        //}
//        //for (Book book: expectedShelf.getBooks()) {
//        //    gson.toJson(new Container(book), fw);
//        //}
//
//        //gson.toJson(expectedShelf.getAllLiterature(), fw);
//        //List list = new ArrayList<>();
//        //list.addAll(expectedShelf.getBooks());
//        //list.addAll(expectedShelf.getMagazines());
////
//        //gson.toJson(list,fw);
//        //gson.toJson(expectedShelf.getMagazines(),fw);
//        //gson.toJson(expectedShelf.getBooks(),fw);
//
//        gson.toJson(expectedShelf.getAllLiterature(),fw);
//
//        List<List<Object>> listFromJson = gson.fromJson(fr, new TypeToken<List<List<Object>>>(){}.getType());
//        Shelf currentShelf = new Shelf(listFromJson);
//        System.out.println(currentShelf.toString());
//
//
//        System.out.println(expectedShelf.getBooks());
//        System.out.println(currentShelf.getBooks());
////
////
////
//        //System.out.println( );
//        //assertEquals(expectedShelf.getBooks(), currentShelf.getBooks());
//    }
//
//    @Test
//    void typeTest() throws IOException {
//        String fileName = "withType.json";
//        FileReader fr = new FileReader( fileName);
//        FileWriter fw = new FileWriter( fileName);
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//
//        List<>
//    }
//}
//
//
//@ToString
//@Data
//class AnotherContainer{
//    List<Container> list = new ArrayList<>();
//    public AnotherContainer(){
//
//    }
//}
//
//@Data
//@AllArgsConstructor
//class TypeContainer <T>{
//    T literature;
//}
//
//@Data
//@NoArgsConstructor
//class Container{
//    String classOfLiterature;
//    Object literature;
//
//    public Container(String classOfLiterature, Object literature){
//        this.classOfLiterature = classOfLiterature;
//        this.literature = literature;
//    }
//
//    public Container(Object literature){
//        this.classOfLiterature = literature.getClass().toString();
//        this.literature = literature;
//    }
//
//}
//
