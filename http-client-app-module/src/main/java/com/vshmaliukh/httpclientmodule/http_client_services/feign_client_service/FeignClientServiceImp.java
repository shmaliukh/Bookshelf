package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.literature_items.Item;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@NoArgsConstructor
public class FeignClientServiceImp extends AbstractHttpShelfService {

    ShelfFeignClientController shelfFeignClientController;

    @Autowired
    public FeignClientServiceImp(ShelfFeignClientController shelfFeignClientController) {
        this.shelfFeignClientController = shelfFeignClientController;
    }

    @PostConstruct
    void postConstructInit() {
        this.userName = "Vlad";
        this.typeOfWork = 4;
        logIn(userName, typeOfWork);

    }

//    public FeignClientServiceImp(ShelfFeignClientController shelfFeignClientController) {

    //    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            requestTemplate.header(MyUtils.COOKIE_HEADER, MyUtils.generateCookieValue(USER_NAME, userName));
//            requestTemplate.header(MyUtils.COOKIE_HEADER, MyUtils.generateCookieValue(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, String.valueOf(typeOfWork)));
//        };
//    }

//    public FeignClientServiceImp(String userName, int typeOfWork) {
//        super(userName, typeOfWork);
//        controller = null;
//    }

    @Override
    public void init() {

    }

    @Override
    public void logIn(String userName, int typeOfWork) {
//        String logIn = shelfFeignClientController.doPost(userName, typeOfWork, null);
    }

    @Override
    public void deleteItemFromDB(Item item) {
        super.deleteItemFromDB(item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        super.changeItemBorrowedStateInDB(item);
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeSimpleName = classType.getSimpleName();
        ResponseEntity<List<? extends Item>> responseEntity = shelfFeignClientController.readItemLisByClassTypeAsGsonStr(userName, typeOfWork, classTypeSimpleName);
        List<? extends Item> itemListFromResponse = responseEntity.getBody();
        return (List<T>) itemListFromResponse;
    }

    @Override
    public void saveItemToDB(Item item) {
        super.saveItemToDB(item);
    }

}
