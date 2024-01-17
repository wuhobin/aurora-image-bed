package com.wuhobin.userservice.controller;

import cn.hutool.json.JSONObject;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wuhobin.common.config.base.BasePlatformConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 七牛云上传 获取临时访问token
 * @author wuhongbin
 * @version 1.0.0
 * @date 2024/1/17 12:05
 * @description
 */
@RestController
@RequestMapping("/qiniu/token")
@Slf4j
public class QiNiuUploadController {

    @Autowired
    private BasePlatformConfig basePlatformConfig;

    @RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String upToken() {
        try {
            Auth mac = Auth.create(basePlatformConfig.getQiNiuAccessKey(), basePlatformConfig.getQiNiuSecretKey());
            String upToken = mac.uploadToken(basePlatformConfig.getQiNiuBucket(),null,3600, new StringMap().put("insertOnly",1));
            JSONObject upTokenJo = new JSONObject();
            upTokenJo.put("uptoken", upToken);
            log.info("生成七牛token，token={}",upToken);
            return upTokenJo.toString();
        } catch (Exception e) {
            log.error("生成七牛token异常!", e);
            return "{}";
        }
    }
}
