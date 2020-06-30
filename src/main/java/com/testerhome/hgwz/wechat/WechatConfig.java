package com.testerhome.hgwz.wechat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

/**
 * @author: gq
 * @createtime: 2020/6/5 8:43 下午
 * @description:
 */
public class WechatConfig {
    public String agentId = "1000002";
    public String secret = "gg099iy_WdeiZTC1U0s6CelgS-GDcvXeuRiKL73LHK40";
    public String corpid = "ww58ad6d6836776138";
    public String contactSecret = "RwmwuDqEs5ZmEeQohH_RQVI-kS9dRPMj6_FsPhJhkV0";

    //单例模式
    private static WechatConfig wechatConfig;

    public static WechatConfig getInstance() {
        if (wechatConfig == null) {
//            wechatConfig = new WechatConfig();
            //从配置文件读取内容
            wechatConfig = load("/conf/wechatConfig.yaml");
            System.out.println(wechatConfig);
            System.out.println(wechatConfig.agentId);
        }
        return wechatConfig;
    }

    public static WechatConfig load(String path) {
        //fixed: read from yaml or json
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
//            System.out.println(mapper.writeValueAsString(WechatConfig.getInstance()));
            return mapper.readValue(WechatConfig.class.getResourceAsStream(path), WechatConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
