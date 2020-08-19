import java.util.ArrayList;

/**
 * @author: gq
 * @createtime: 2020/8/18 2:55 下午
 * @description: 发送钉钉消息
 */

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DingPush {
    static String token_url = "https://oapi.dingtalk.com/robot/send?access_token=6d5889e8f4f4184b207ec2b1ed4bdafe423ba391b76287bf224741c403bd9034"; //钉钉的自定义机器人，webhook值
    static String jobname = "WechatTest";

    static String lastnum = getLastBuildNum();//该job最后一个构建号

    public static void main(String[] args) {
        DingPush.dingPush();
    }

    public static void dingPush() {
        DingPush push = new DingPush();
        ArrayList<String> arrayList = push.testStatus();
        String retries_run = arrayList.get(0);//获取运行总数并赋值
        String status_passed = arrayList.get(1);//获取通过数量并赋值
        String status_failed = arrayList.get(2);//获取失败数量并赋值
        String job_url = "http://localhost:8888/job/" + jobname;
        String report_url = job_url + "/" + lastnum + "/allure";
        given().log().all()
                .contentType(ContentType.JSON)
                .body("{\"msgtype\":\"text\",\n" +
                        "\"text\":{\"content\":" +
                        "\n\"测试结果：" +
                        "\n运行总数:" + retries_run +
                        "\n通过数量:" + status_passed +
                        "\n失败数量:" + status_failed +
                        "\n构建地址:" + job_url +
                        "\n报告地址:" + report_url + "\"}}")
                .when().post(token_url)
                .then().log().all()
                .statusCode(200)
                .extract().response();
    }


    /*
        return:ArrayList包含所有运行状态
     */
    public ArrayList<String> testStatus() {
        ArrayList arrayList = new ArrayList();
        Response response = given()
                .cookie("screenResolution=1440x900; jenkins-timestamper-offset=-28800000; JSESSIONID.877338c5=node04jlzmyij8nomqluegyccyn6423.node0; JSESSIONID.d2f68551=node0zter0mdo5hrr17o7v0icuuqs31.node0; JSESSIONID.d2d75a68=node01t9vdwng9cad22a1d1xvitz551.node0; JSESSIONID.e15df850=node014379dpzkes6d184z445vfxmhs1.node0; JSESSIONID.34982c1d=node0kd2kggeon83k1f58qzcnrmfov1.node0; JSESSIONID.929f55bf=node0eaza778qwzwv1s7nswbgzmzdh1.node0; JSESSIONID.346abbce=node0172fnpys6amya1rohssbax6gcp1.node0; JSESSIONID.92817d9a=node013muzf2d2qu5p6pzkuhw34now1.node0; JSESSIONID.c68ae225=node02oz8b6mj0qye1jtg5ou0st6t130.node0")
                .when().get("http://localhost:8888/job/" + jobname + "/" + lastnum + "/allure/widgets/summary.json")
                .then()//.log().all()
                .statusCode(200)
                .extract().response();
        String total = response.jsonPath().getString("statistic.total");
        String passed = response.jsonPath().getString("statistic.passed");
        String failed = response.jsonPath().getString("statistic.failed");
        String skipped = response.jsonPath().getString("statistic.skipped");
        String broken = response.jsonPath().getString("statistic.broken");
        String unknown = response.jsonPath().getString("statistic.unknown");
        arrayList.add(total);
        arrayList.add(passed);
        arrayList.add(failed);
        arrayList.add(broken);
        arrayList.add(unknown);
        arrayList.add(skipped);
        return arrayList;
    }

    /*
        return:最后一次构建号
     */
    public static String getLastBuildNum() {
        Response response = given()
                .contentType(ContentType.JSON)
                .cookie("screenResolution=1440x900; jenkins-timestamper-offset=-28800000; JSESSIONID.877338c5=node04jlzmyij8nomqluegyccyn6423.node0; JSESSIONID.d2f68551=node0zter0mdo5hrr17o7v0icuuqs31.node0; JSESSIONID.d2d75a68=node01t9vdwng9cad22a1d1xvitz551.node0; JSESSIONID.e15df850=node014379dpzkes6d184z445vfxmhs1.node0; JSESSIONID.34982c1d=node0kd2kggeon83k1f58qzcnrmfov1.node0; JSESSIONID.929f55bf=node0eaza778qwzwv1s7nswbgzmzdh1.node0; JSESSIONID.346abbce=node0172fnpys6amya1rohssbax6gcp1.node0; JSESSIONID.92817d9a=node013muzf2d2qu5p6pzkuhw34now1.node0; JSESSIONID.c68ae225=node02oz8b6mj0qye1jtg5ou0st6t130.node0")
                .queryParam("pretty", "true")
                .when().get("http://localhost:8888/job/" + jobname + "/api/json")
                .then()//.log().all()
                .statusCode(200)
                .extract().response();
        String lastnum = response.jsonPath().getString("builds.number[0]");
        return lastnum;
    }
}

