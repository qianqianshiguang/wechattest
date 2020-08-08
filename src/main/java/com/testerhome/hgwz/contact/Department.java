package com.testerhome.hgwz.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.testerhome.hgwz.wechat.Api;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;


/**
 * @author: gq
 * @createtime: 2020/6/8 10:24 上午
 * @description: 部门
 */
public class Department extends Api {

    public Response list(String id) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", id);
        return templateFromYaml("/api/list.yaml", hashMap);
    }

    public Response create(String name, String parentid) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("$.name", name);
        hashMap.put("$.parentid", parentid);
        hashMap.put("_file", "/data/create.json");
        return templateFromYaml("/api/create.yaml", hashMap);
    }

    public Response createMap(HashMap<String, Object> hashMap) {
        DocumentContext documentContext = JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"));
        hashMap.put("_file", "/data/create.json");
        return templateFromYaml("/api/create.yaml", hashMap);
    }

    public Response delete(String id) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", id);
        return templateFromYaml("/api/delete.yaml", hashMap);
    }

    public Response deleteAll() {
        List<Integer> idList = list("").then().extract().path("department.id");
        idList.forEach(id -> {
            delete(id.toString());
        });
        return null;
    }

    public Response update(String id, String name) {
        HashMap hashMap = new HashMap();
        hashMap.put("$.id", id);
        hashMap.put("$.name", name);
        hashMap.put("_file", "/data/update.json");
        return templateFromYaml("/api/update.yaml", hashMap);
    }

    public Response updateMap(HashMap<String, Object> hashMap) {
        hashMap.put("_file", "/data/update.json");
        return templateFromYaml("/api/update.yaml", hashMap);
    }
}
