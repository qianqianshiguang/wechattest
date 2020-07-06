import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author: gq
 * @createtime: 2020/7/1 2:21 下午
 * @description: TODO
 */
public class Demo {
    @Test
    public void testJson() {
        given().when().get("http://0.0.0.0:8000/testerhome.json")
                .then().body("lotto.lottoId", equalTo(5));
    }

}
