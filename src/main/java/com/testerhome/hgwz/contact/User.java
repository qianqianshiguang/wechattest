package com.testerhome.hgwz.contact;

import com.testerhome.hgwz.wechat.Restful;
import io.restassured.response.Response;

import java.util.HashMap;

/**
 * @author: gq
 * @createtime: 2020/6/8 10:39 上午
 * @description: 用户
 */
public class User extends Contact{
    public Response create(String url, HashMap<String, Object> hashMap) {
        String body = Restful.template("/data/user.json", hashMap);
        contact();
        Response response = requestSpecification
                .body(body).when().post(url)
                .then().extract().response();
        return response;
    }

}
