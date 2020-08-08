package com.testerhome.hgwz.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * @author: gq
 * @createtime: 2020/6/9 11:50 上午
 * @description: TODO
 */
public class Api {

    public RequestSpecification getDefaultRequestSpecification() {
        return given().log().all()
                .queryParam("access_token", Wechat.getToken())
                .contentType(ContentType.JSON);

    }

    public static String template(String path, HashMap<String, Object> hashMap) {
        DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public Response templateFromHar(String path, String pattern, HashMap<String, Object> hashMap) {
        //todo:支持从har文件读取接口定义并发送
        //从har中读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        String method = documentContext.read("method");
        String url = documentContext.read("url");
        return getDefaultRequestSpecification().when().request(method, url);
    }

    public Response templateFromSwagger(String path, String pattern, HashMap<String, Object> hashMap) {
        //todo:支持从swagger文件读取接口定义并发送
        //从swagger中读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        String method = documentContext.read("method");
        String url = documentContext.read("url");
        return getDefaultRequestSpecification().when().request(method, url);
    }

    public Response templateFromYaml(String path, HashMap<String, Object> hashMap) {
        //fixed:支持从yaml文件读取接口定义并发送
        //从yaml中读取请求，进行更新
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            //将配置文件内容以yaml写入
//            System.out.println(mapper.writeValueAsString(WechatConfig.getInstance()));
            Restful restful = mapper.readValue(Restful.class.getResourceAsStream(path), Restful.class);
            if (restful.method.toLowerCase().contains("get")) {
                hashMap.entrySet().forEach(entry -> {
                    restful.query.replace(entry.getKey(), entry.getValue().toString());
                });
            }
            RequestSpecification requestSpecification = getDefaultRequestSpecification();
            if (restful.method.toLowerCase().contains("post")) {
                if (hashMap.containsKey("_file")) {
                    String filePath = hashMap.get("_file").toString();
                    hashMap.remove("_file");
                    restful.body = template(filePath, hashMap);
                }
            }

            if (restful.query != null) {
                restful.query.entrySet().forEach(entry -> {
                    requestSpecification.queryParam(entry.getKey(), entry.getValue());
                });
            }
            if (restful.body != null) {
                requestSpecification.body(restful.body);
            }

            return requestSpecification.
                    request(restful.method, restful.url).then().log().all().extract().response();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
