package com.testerhome.hgwz.wechat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: gq
 * @createtime: 2020/6/30 10:45 上午
 * @description: TODO
 */
class WechatConfigTest {

    @Test
    void load() {
        WechatConfig.load("");
    }

    @Test
    void getInstance() {
        WechatConfig.getInstance();
    }
}