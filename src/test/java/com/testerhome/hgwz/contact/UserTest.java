package com.testerhome.hgwz.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author: gq
 * @createtime: 2020/6/10 4:25 下午
 * @description: TODO
 */
class UserTest {
    User user;
    String random = String.valueOf(System.currentTimeMillis());
    @BeforeEach
    void setUp() {
        if (user == null) {
            user = new User();
        }
    }
    @ParameterizedTest
    @ValueSource(strings = {"wang", "li", "xiao"})
//    @Test
    void create(String name) {

        String nameNew = name + random;
        String ran = String.valueOf(System.currentTimeMillis()).substring(5 + 0, 5 + 8);
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", nameNew);
        hashMap.put("name", nameNew);
        hashMap.put("mobile", "" + "151" + ran);
        hashMap.put("department", Arrays.asList(1, 2));
        hashMap.put("email", ran + "@qq.com");
        user.create(url, hashMap).then().statusCode(200).body("errcode", equalTo(0));

    }
    @ParameterizedTest
    @CsvFileSource(resources = "/data/user.csv")
    void create1(String name,String alias) {

        String nameNew = name + random;
        String ran = String.valueOf(System.currentTimeMillis()).substring(5 + 0, 5 + 8);
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", nameNew);
        hashMap.put("alias", alias);
        hashMap.put("name", nameNew);
        hashMap.put("mobile", "" + "151" + ran);
        hashMap.put("department", Arrays.asList(1, 2));
        hashMap.put("email", ran + "@qq.com");
        user.create(url, hashMap).then().statusCode(200).body("errcode", equalTo(0));

    }

}