package org.vshmaliukh;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TestOptional {

    @Test
    public void test() {

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonObject());
        for (JsonElement element : jsonArray) {
            Optional<Object> itemObject =
                    Optional.ofNullable(element)
                            .map(JsonElement::getAsJsonObject)
                            .map(o -> o.getAsJsonObject("literature"));

            if(element!=null){
                JsonObject asJsonObject = element.getAsJsonObject();
                if(asJsonObject!=null){

                }
            }
            System.out.println(itemObject);
            if (!itemObject.isPresent()) {
                System.out.println("Все погано");
            }
        }
    }
}
