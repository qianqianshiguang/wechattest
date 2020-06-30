package com.testerhome.hgwz.wechat;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.util.HashMap;

import static io.restassured.RestAssured.given;


/**
 * @author: gq
 * @createtime: 2020/6/9 11:50 上午
 * @description:
 */
public class Restful {
    public static RequestSpecification requestSpecification = RestAssured.given();
//    HashMap<String, Object> query = new HashMap<String, Object>();
//    public Response send() {
//        requestSpecification = given().log().all();
//        query.entrySet().forEach(entry -> {
//            requestSpecification.queryParam(entry.getKey(),entry.getValue());
//        });
//        return requestSpecification.when().request("get", "www.baidu.com");
//    }

    public static String template(String jsonname, HashMap<String, Object> hashMap) {
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(jsonname));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public static Response templateForHar(String path, String url1, HashMap<String, Object> hashMap) {
        //TODO 支持从Har自动生成接口定义并发送
        //从Har中读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(path));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        String method = documentContext.read("method");
        String url = documentContext.read("url");
        return requestSpecification.when().request(method, url);
    }
    public static Response templateForSwagger(String path, String url1, HashMap<String, Object> hashMap) {
        //TODO 支持从swagger自动生成接口定义并发送
        //从Har中读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(path));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        String method = documentContext.read("method");
        String url = documentContext.read("url");
        return requestSpecification.when().request(method, url);
    }

    public Response templateFromYaml(String path, HashMap<String, Object> hashMap) {
        //TODO 根据yaml生成接口定义并发送
        return null;
    }

    //TODO 支持wsdl soap
    public Response api(String path, HashMap<String, Object> hashMap) {
        //动态调用
        return null;
    }
}
