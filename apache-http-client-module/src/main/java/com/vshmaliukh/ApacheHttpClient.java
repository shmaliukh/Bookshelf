package com.vshmaliukh;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApacheHttpClient {

    //HTTP/1.1 methods specification: GET, HEAD, POST, PUT, DELETE, TRACE OPTIONS
    //method type.: HttpGet, HttpHead, HttpPost, HttpPut, HttpDelete, HttpTrace and HttpOptions.
    //HTTP request URIs consist of a protocol scheme, host name, optional port, resource path, optional query, and optional fragment.

    public static void main(final String[] args) {

        ApacheHttpShelfService apacheHttpShelfService = new ApacheHttpShelfService("Vlad", 4);
        apacheHttpShelfService.readItemList();

//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(PAGE_TO_HTTP_GET);
//        httpPost.setHeader("Cookie", "userName=Vlad");
//        httpPost.setHeader("Cookie", "typeOfWork=4");
//        httpPost.setHeader("Cookie", "itemClassType=Magazine");
//
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
//
//        StringEntity magazineStringEntity = new StringEntity("{NAME: \"1111\", PAGES: \"1111\", BORROWED: \"y\"}");
//        httpPost.setEntity(magazineStringEntity);
//
//        CloseableHttpResponse response = client.execute(httpPost);
//        HttpEntity responseEntity = response.getEntity();
//        String responseEntityStr = EntityUtils.toString(responseEntity);
//        System.out.println("getHeaders: " + Arrays.toString(response.getHeaders()));
//        System.out.println("getEntity: " + responseEntity);
//        System.out.println("responseEntityStr: " + responseEntityStr);
//
//        HttpGet httpGet = new HttpGet("http://localhost:8082/test");
//
//        try {
//            CloseableHttpResponse response = client.execute(httpGet);
//            HttpEntity responseEntity = response.getEntity();
//            String responseEntityStr = EntityUtils.toString(responseEntity);
//            System.out.println("getHeaders: " + Arrays.toString(response.getHeaders()));
//            System.out.println("getEntity: " + responseEntity);
//            System.out.println("responseEntityStr: " + responseEntityStr);
//        } catch (Exception e) {
////            MyLogUtil.logErr(this, e);
//        }

    }

}
