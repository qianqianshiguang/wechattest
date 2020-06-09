package com.testerhome.hgwz.wechat;

/**
 * @author: gq
 * @createtime: 2020/6/5 8:43 下午
 * @description: TODO
 */
public class WechatConfig {
    public String agentId = "1000002";
    public String Secert = "gg099iy_WdeiZTC1U0s6CelgS-GDcvXeuRiKL73LHK40";
    public String corpid = "ww58ad6d6836776138";
    public String contactSecert = "RwmwuDqEs5ZmEeQohH_RQVI-kS9dRPMj6_FsPhJhkV0";

    //单例模式
    private static WechatConfig wechatConfig;

    public static WechatConfig getInstance() {
        if (wechatConfig == null) {
            wechatConfig = new WechatConfig();
        }
        return wechatConfig;
    }

    public static void load(String pass) {
        //read from yml to json
    }


}
