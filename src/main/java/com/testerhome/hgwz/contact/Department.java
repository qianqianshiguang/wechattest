package com.testerhome.hgwz.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;


/**
 * @author: gq
 * @createtime: 2020/6/8 10:24 上午
 * @description: TODO
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

    public Response delete(String id) {
        contact();
        Response response = requestSpecification
                .param("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().statusCode(200).extract().response();
        return response;
    }

    public Response update(String id, String name) {
        contact();
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/update.json"))
                .set("$.id", id).set("$.name", name)
                .jsonString();

        Response response = requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().statusCode(200).extract().response();
        return response;
    }
}
