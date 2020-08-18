package com.testerhome.hgwz.contact;

import groovy.beans.ListenerList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author: gq
 * @createtime: 2020/6/8 12:27 下午
 * @description: 部门测试用例设计
 */
@DisplayName("部门测试")
public class DepartmentTest {
    Department department;
    String random;
    @BeforeEach
    public void setUp() {
        if (department == null) {
            department = new Department();
            department.deleteAll();
        }
        random = String.valueOf(System.currentTimeMillis()).substring(5 + 0, 5 + 8);
    }
    @Test
    @DisplayName("部门列表")
    public void list() {
        department.list("").then().statusCode(200).body("department.name[0]",equalTo("明我"));
//        department.list("2").then().statusCode(200).body("department.name[0]", equalTo("产品部门"))
//                .body("department.id[0]", equalTo(2));
    }

    @Test
    @DisplayName("部门创建")
    public void create() {
        String name = "测试部门" + random;
        department.create(name,"1").then().body("errcode",equalTo(0));

    }
    @Test
    @DisplayName("已存在部门创建")
    public void createExisted() {
        String name = "测试部门" + random;
        String id = department.create(name, "1").path("id").toString();
        department.create("测试部门"+random,"1").then().body("errcode",equalTo(60008));
        department.delete(id);
    }

    @Test
    @DisplayName("map创建部门")
    public void createMap() {
        String name = "测试部门" + random;
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("name", name);
        hashMap.put("parentid", 1);
        hashMap.put("name_en", null);
        hashMap.put("order", null);
        hashMap.put("id", null);
        department.createMap(hashMap).then().body("errcode",equalTo(0));
    }

    @Test
    @DisplayName("部门删除")
    public void delete() {
        String id = department.create("测试部门"+random, "1").path("id").toString();
        department.delete(id).then().body("errcode",equalTo(0)).body("errmsg",equalTo("deleted"));
    }

    @Test
    @DisplayName("部门更新")
    public void update() {
        String id = department.create("测试部门"+random, "1").path("id").toString();
        department.update(id, "测试部门101"+random)
                .then().body("errcode", equalTo(0)).body("errmsg", equalTo("updated"));
        department.delete(id);
    }

    @Test
    @DisplayName("map更新部门")
    public void updateMap() {
        String id = department.create("测试部门"+random, "1").path("id").toString();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", id);
        hashMap.put("name", "测试部门101"+random);
        hashMap.put("parentid", 1);
        hashMap.put("name_en", null);
        hashMap.put("order", null);
        department.updateMap(hashMap)
                .then().body("errcode", equalTo(0)).body("errmsg", equalTo("updated"));
        department.delete(id);
    }

//    @Test
//    public void deleteAll() {
//        department.deleteAll();
//
//    }
}