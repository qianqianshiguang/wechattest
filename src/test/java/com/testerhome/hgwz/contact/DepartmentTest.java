package com.testerhome.hgwz.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.testerhome.hgwz.contact.Contact.random;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author: gq
 * @createtime: 2020/6/8 12:27 下午
 * @description: TODO
 */
class DepartmentTest {
    Department department;
//    String random = String.valueOf(System.currentTimeMillis());
    @BeforeEach
    void setUp() {
        if (department == null) {
            department = new Department();
        }
    }

    @Test
    void list() {
//        department.list("").then().statusCode(200).body("department.name[0]",equalTo("明我"));
        department.list("2").then().statusCode(200).body("department.name[0]", equalTo("产品部门"))
                .body("department.id[0]", equalTo(2));
    }

    @Test
    void create() {
        String name = "测试部门" + random;
        department.create(name,"1").then().body("errcode",equalTo(0));
        String id = department.list("").path("department.find{ it.name == super.name}.id").toString();
        department.delete(id);

    }
    @Test
    void createExisted() {
        String name = "测试部门" + random;
        String id = department.create(name, "1").path("id").toString();
        department.create("测试部门"+random,"1").then().body("errcode",equalTo(60008));
        department.delete(id);
    }

    @Test
    void createMap() {
        String name = "测试部门" + random;
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("name", name);
        hashMap.put("parentid", 1);
        hashMap.put("name_en", null);
        hashMap.put("order", null);
        hashMap.put("id", null);
        department.createMap(hashMap).then().body("errcode",equalTo(0));
    }

    @Test
    void delete() {
        String id = department.create("测试部门"+random, "1").path("id").toString();
        department.delete(id).then().body("errcode",equalTo(0)).body("errmsg",equalTo("deleted"));
    }

    @Test
    void update() {
        String id = department.create("测试部门"+random, "1").path("id").toString();
        department.update(id, "测试部门101"+random)
                .then().body("errcode", equalTo(0)).body("errmsg", equalTo("updated"));
        department.delete(id);
    }

    @Test
    void updateMap() {
        String id = department.create("测试部门"+random, "1").path("id").toString();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("name", "测试部门101"+random);
        hashMap.put("parentid", 1);
        hashMap.put("name_en", null);
        hashMap.put("order", null);
        department.updateMap(hashMap)
                .then().body("errcode", equalTo(0)).body("errmsg", equalTo("updated"));
        department.delete(id);
    }

}