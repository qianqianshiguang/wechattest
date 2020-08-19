import java.util.ArrayList;
import java.util.Map;

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

    static String cookie = "screenResolution=1440x900; " + getCookie();//获取Jenkins cookie
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
//

    /*
        return:ArrayList包含所有运行状态
     */
    public ArrayList<String> testStatus() {
        ArrayList arrayList = new ArrayList();
        Response response = given()
                .cookie(cookie)
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
                .cookie(cookie)
                .queryParam("pretty", "true")
                .when().get("http://localhost:8888/job/" + jobname + "/api/json")
                .then()//.log().all()
                .statusCode(200)
                .extract().response();
        String lastnum = response.jsonPath().getString("builds.number[0]");
        return lastnum;
    }

    public static String getCookie() {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("j_username", "root")
                .queryParam("j_password", "love3224")
                .when().post("http://127.0.0.1:8888/j_acegi_security_check")
                .then()//.log().all()
                .extract().response();
        Map<String, String> allCookies = response.getCookies();
        ArrayList<String> list = new ArrayList<String>();
        allCookies.entrySet().forEach(entry -> {
            list.add(entry.getKey() + "=" + entry.getValue());
        });
        String cookie = list.get(0);
        System.out.println(cookie);
        return cookie;

    }
}

