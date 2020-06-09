import com.testerhome.hgwz.wechat.Wechat;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author: gq
 * @createtime: 2020/6/5 8:22 下午
 * @description: TODO
 */
public class TestGetToken {
    @Test
    public void testToken() {
        Wechat wechat = new Wechat();
        String token = wechat.getWechatToken();
        assertThat(token, not(equalTo(null)));

    }
}
