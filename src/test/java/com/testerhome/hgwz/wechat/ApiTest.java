package com.testerhome.hgwz.wechat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: gq
 * @createtime: 2020/7/27 5:01 下午
 * @description: TODO
 */
class ApiTest {

    @Test
    void templateFromYaml() {
        Api api = new Api();
        api.templateFromYaml("/api/list.yaml", null).then().statusCode(200);
    }
}