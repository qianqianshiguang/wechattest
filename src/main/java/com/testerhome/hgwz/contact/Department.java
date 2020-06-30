package com.testerhome.hgwz.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.testerhome.hgwz.wechat.Restful;
import com.testerhome.hgwz.wechat.Wechat;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;


/**
 * @author: gq
 * @createtime: 2020/6/8 10:24 上午
 * @description:
 */
public class Department extends Reset {

    public Response list(String id) {
        reset();
        Response response = requestSpecification
                .param("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().extract().response();
        return response;
    }

    public Response create(String name,String parentid) {
        reset();
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"))
                .set("$.name", name).set("$.parentid", parentid).jsonString();
        Response response = requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().extract().response();
        return response;
    }

    public Response createMap(HashMap<String,Object> hashMap) {
//        DocumentContext documentContext = JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"));
//        hashMap.entrySet().forEach(entry -> {
//            documentContext.set(entry.getKey(), entry.getValue());
//                });
        reset();
        String body = template("/data/create.json", hashMap);
        Response response = requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().extract().response();
        return response;
    }
    public Response delete(String id) {
        reset();
        Response response = requestSpecification
                .param("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().extract().response();
        return response;
    }
    public void deleteAll() {
        reset();
        List<Integer> ids = list("").then().log().all().extract().path("department.id");
        ids.forEach(id->{
            RestAssured.given().log().all()
                    .param("access_token", Wechat.getToken())
                    .param("id", id)
                    .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                    .then().extract().response();
//            delete(id.toString());
        });
    }

    public Response update(String id, String name) {
        reset();
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/update.json"))
                .set("id", id)
                .set("name", name).jsonString();
        Response response = requestSpecification.body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().extract().response();
        return response;
    }

    public Response update1(HashMap<String, Object> hashMap) {

        Response response = templateForHar(
                "/data/demo.har.json",
                "https://work.weixin.qq.com/wework_admin/party?action=addparty",
                hashMap);
        return response;
    }

    public Response updateAll(HashMap<String, Object> hashMap) {
        return api("api.json", hashMap);
    }

    public Response updateMap(HashMap<String, Object> hashMap) {
        reset();
        String body = template("/data/update.json", hashMap);
        Response response = requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().extract().response();
        return response;
    }
}
