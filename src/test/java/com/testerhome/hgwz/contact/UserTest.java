package com.testerhome.hgwz.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author: gq
 * @createtime: 2020/6/10 4:25 下午
 * @description: TODO
 */
public class UserTest {
    public User user;
    String random;
    @BeforeEach
    void setUp() {
        if (user == null) {
            user = new User();
        }
        random = String.valueOf(System.currentTimeMillis()).substring(5 + 0, 5 + 8);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wang", "li", "xiao"})
    void create(String name) {

        String nameNew = name + random;
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("userid", nameNew);
        hashMap.put("name", nameNew);
        hashMap.put("mobile", "" + "148" + random);
        hashMap.put("department", Arrays.asList(1, 2));
        hashMap.put("email", random + "@qq.com");
        user.create(url, hashMap).then().statusCode(200).body("errcode", equalTo(0));

    }

}