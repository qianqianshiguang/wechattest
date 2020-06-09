package com.testerhome.hgwz.wechat;

import io.restassured.RestAssured;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author: gq
 * @createtime: 2020/6/5 9:00 下午
 * @description: TODO
 */
public class Wechat {
    private static String token;
    public static String getWechatToken() {
        return RestAssured.given()
                .queryParam("corpid", WechatConfig.getInstance().corpid)
                .queryParam("corpsecret", WechatConfig.getInstance().contactSecert)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .statusCode(200)
                .body("errcode", equalTo(0))
                .extract().path("access_token");
    }

    public static String getToken() {
        if (token == null) {
            token = getWechatToken();
        }
        return token;
    }
}
