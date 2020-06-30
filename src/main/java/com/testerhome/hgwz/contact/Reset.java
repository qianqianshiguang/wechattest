package com.testerhome.hgwz.contact;
import com.testerhome.hgwz.wechat.Restful;
import com.testerhome.hgwz.wechat.Wechat;
import io.restassured.http.ContentType;

/**
 * @author: gq
 * @createtime: 2020/6/9 11:50 上午
 * @description: TODO
 */
public class Reset extends Restful {

    public void reset() {

        requestSpecification.given().log().all()
                .queryParam("access_token", Wechat.getToken())
                .contentType(ContentType.JSON)
                .then().log().all().statusCode(200);
    }
}
