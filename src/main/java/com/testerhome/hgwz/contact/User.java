package com.testerhome.hgwz.contact;

import com.testerhome.hgwz.wechat.Api;
import io.restassured.response.Response;

import java.util.HashMap;

/**
 * @author: gq
 * @createtime: 2020/6/8 10:39 上午
 * @description: 用户
 */
public class User extends Api{
    public Response create(String url, HashMap<String, Object> hashMap) {
        String body = Api.template("/data/user.json", hashMap);
        Response response = getDefaultRequestSpecification()
                .body(body).when().post(url)
                .then().extract().response();
        return response;
    }

}
