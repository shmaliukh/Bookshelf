package com.vshmaliukh.httpclientmodule;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractHttpShelfService;
import com.vshmaliukh.httpclientmodule.http_client_services.MyHttpClientUtils;
import com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service.ShelfFeignClient;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.http_client_services.MyHttpClientUtils.informAboutResponseStatus;

@Slf4j
@NoArgsConstructor
public abstract class AbstractSpringClientService extends AbstractHttpShelfService {

    protected ShelfFeignClient shelfFeignClientController;

    @Override
    public void init() {

    }

    @Override
    public void deleteItemFromDB(Item item) {
        int itemIndex = readItemList().indexOf(item) + 1;
        ResponseEntity<Void> responseEntity = shelfFeignClientController.deleteItemAndGetResponse(userName, typeOfWork, itemIndex);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_DELETED_ITEM_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_DELETE_ITEM_MESSAGE_STR,
                userName, typeOfWork, item);
    }

    @Override
    public void changeItemBorrowedStateInDB(Item item) {
        int itemIndex = readItemList().indexOf(item) + 1;
        ResponseEntity<Void> responseEntity = shelfFeignClientController.changeItemBorrowedStateAndGetResponse(userName, typeOfWork, itemIndex);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_CHANGED_BORROWED_STATE_FOR_ITEM_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_CHANGE_BORROWED_STATE_FOR_ITEM_MESSAGE_STR,
                userName, typeOfWork, item);
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        String classTypeSimpleName = classType.getSimpleName();
        ResponseEntity<List<? extends Item>> responseEntity = shelfFeignClientController.readItemListByClassType(userName, typeOfWork, classTypeSimpleName);
        List<? extends Item> itemListFromResponse = responseEntity.getBody();
        return (List<T>) itemListFromResponse;
    }

    @Override
    public void saveItemToDB(Item item) {
        ResponseEntity<Void> responseEntity = shelfFeignClientController.saveItemAndGetResponse(userName, typeOfWork, item);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_ADDED_ITEM_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_ADD_ITEM_TO_DB_MESSAGE_STR,
                userName, typeOfWork, item);
    }
}
