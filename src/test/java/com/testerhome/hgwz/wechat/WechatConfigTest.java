package com.testerhome.hgwz.wechat;


import org.junit.jupiter.api.Test;

/**
 * @author: gq
 * @createtime: 2020/7/1 11:10 上午
 * @description: TODO
 */
public class WechatConfigTest {

    @Test
    public void load() {
        WechatConfig.load("/config/wechatConfig.yaml");
    }

    @Test
    public void getInstance() {
        WechatConfig.getInstance();
    }
}