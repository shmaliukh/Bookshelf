package com.vshmaliukh.httpclientmodule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class HttpClientAppConfig {

    public static final int APACHE_HTTP_MODE_WORK = 1;
    public static final int REST_TEMPLATE_MODE_WORK = 2;
    public static final int FEIGN_MODE_WORK = 3;

    public static final String HTTP_LOCALHOST_8082 = "http://localhost:8082/";
    public static final String APPLICATION_JSON = "application/json";
    public static final String INDEX_OF_ITEM = "indexOfItem";
    public static final String ITEM_CLASS_TYPE = "itemClassType";
    public static final String LOG_IN_PAGE = "log_in";
    public static final String DELETE_ITEM_PAGE = "delete_item";
    public static final String CHANGE_ITEM_BORROWED_STATE_PAGE = "change_item_borrowed_state";
    public static final String READ_ITEMS_BY_TYPE_PAGE = "read_items_by_type";
    public static final String ADD_ITEM_PAGE = "add_item";
    public static final String ADD_ITEM_URI_STR = HTTP_LOCALHOST_8082 + HttpClientAppConfig.ADD_ITEM_PAGE;

    public static final String LOG_IN_PAGE_URL_STR = HTTP_LOCALHOST_8082 + LOG_IN_PAGE;
    public static final String DELETE_ITEM_BY_INDEX_URL_STR = HTTP_LOCALHOST_8082 + DELETE_ITEM_PAGE;
    public static final String CHANGE_ITEM_BORROWED_STATE_BY_INDEX_URL_STR = HTTP_LOCALHOST_8082 + CHANGE_ITEM_BORROWED_STATE_PAGE;
    public static final String READ_ITEMS_BY_TYPE_URL_STR = HTTP_LOCALHOST_8082 + READ_ITEMS_BY_TYPE_PAGE;

    public static String MESSAGE_ENTER_TYPE_OF_WORK_WITH_HTTP_CLIENT = "Enter type number of http client work: (program ignores all not number symbols)" + System.lineSeparator();
    public static final Pattern PATTERN_FOR_TYPE_OF_HTTP_CLIENT_WORK;

    public static final Map<Integer, String> HTTP_CLIENT_TYPE_OF_WORK_MAP;

    private HttpClientAppConfig() {
    }

    static {
        HashMap<Integer, String> tempHttpClientTypeOfWorkMap = new HashMap<>();
        tempHttpClientTypeOfWorkMap.put(APACHE_HTTP_MODE_WORK, "work with apache http");
        tempHttpClientTypeOfWorkMap.put(REST_TEMPLATE_MODE_WORK, "work with rest template");
        tempHttpClientTypeOfWorkMap.put(FEIGN_MODE_WORK, "work with feign");
        HTTP_CLIENT_TYPE_OF_WORK_MAP = Collections.unmodifiableMap(tempHttpClientTypeOfWorkMap);


        HTTP_CLIENT_TYPE_OF_WORK_MAP.forEach((k, v) -> MESSAGE_ENTER_TYPE_OF_WORK_WITH_HTTP_CLIENT += (k + " - " + v + System.lineSeparator())); // todo refactor
        MESSAGE_ENTER_TYPE_OF_WORK_WITH_HTTP_CLIENT = MESSAGE_ENTER_TYPE_OF_WORK_WITH_HTTP_CLIENT.trim();

        PATTERN_FOR_TYPE_OF_HTTP_CLIENT_WORK = Pattern.compile("^[1-" + HTTP_CLIENT_TYPE_OF_WORK_MAP.keySet().size() + "]*$");
    }
}
