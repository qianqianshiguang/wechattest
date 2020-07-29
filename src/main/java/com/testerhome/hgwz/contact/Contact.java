package com.testerhome.hgwz.contact;

import com.testerhome.hgwz.wechat.Api;
import com.testerhome.hgwz.wechat.Wechat;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

/**
 * @author: gq
 * @createtime: 2020/6/9 11:50 上午
 * @description: TODO
 */
public class Contact extends Api {
    public void contact() {
        reset();
    }
    public void reset() {
        requestSpecification = given().log().all()
                .queryParam("access_token", Wechat.getToken())
                .contentType(ContentType.JSON);
    }
}
