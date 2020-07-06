package com.testerhome.hgwz.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;


/**
 * @author: gq
 * @createtime: 2020/6/8 10:24 上午
 * @description: 部门
 */
public class Department extends Contact{

    public Response list(String id) {
        contact();
        Response response = requestSpecification
                .param("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().extract().response();
        return response;
    }

    public Response create(String name,String parentid) {
        contact();
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"))
                .set("$.name", name).set("$.parentid", parentid).jsonString();
        Response response = requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().extract().response();
        return response;
    }

    public Response createMap(HashMap<String,Object> hashMap) {

        contact();
        DocumentContext documentContext = JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
                });
        Response response = requestSpecification
                .body(documentContext.jsonString())
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().extract().response();
        return response;
    }
    public Response delete(String id) {
        contact();
        Response response = requestSpecification
                .param("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().extract().response();
        return response;
    }

    public void deleteAll() {
        contact();
        String id = requestSpecification
                .param("")
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .path("id").toString();
        String[] ids = id.split(",");

    }

    public Response update(String id, String name) {
        contact();
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/update.json"))
                .set("id", id)
                .set("name", name).jsonString();
        Response response = requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().extract().response();
        return response;
    }

    public Response updateMap(HashMap<String, Object> hashMap) {
        contact();
        DocumentContext documentContext = JsonPath.parse(this.getClass().getResourceAsStream("/data/update.json"));
        hashMap.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        Response response = requestSpecification
                .body(documentContext.jsonString())
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().extract().response();
        return response;
    }
}
