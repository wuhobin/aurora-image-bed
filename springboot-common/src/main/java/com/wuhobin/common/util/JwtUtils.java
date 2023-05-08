package com.wuhobin.common.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wuhobin.common.util.security.AESUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhobin
 * @create 2023/05/15 10:43
 * @desc jwt加解密
 **/
@Slf4j
public class JwtUtils {

    /**
     * 签名秘钥
     */
    private static final String BASE64_SECRET = "common_sign_key";

    /**
     * 用于JWT加密的密匙
     */
    private static final String DATA_KEY = "common_jwt_key";

    /**
     * 超时毫秒数（默认120分钟）
     */
    private static final int EXPIRES_SECOND = 2 * 60 * 60 * 1000;


    /**
     * 生成JWT字符串
     */
    public static String generateJWT(String userId, String uniqueId) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BASE64_SECRET);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = null;
        try {
            builder = Jwts.builder().setHeader(headMap)
                    //加密后的openId
                    .claim("userId", AESUtil.encrypt(userId, DATA_KEY))
                    .claim("uniqueId", AESUtil.encrypt(uniqueId, DATA_KEY))
                    //Signature
                    .signWith(signatureAlgorithm, signingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加Token过期时间
        if (EXPIRES_SECOND >= 0) {
            long expMillis = nowTimeMillis + EXPIRES_SECOND;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate).setNotBefore(now);
        }
        return builder.compact();
    }

    /**
     * 生成JWT字符串
     */
    public static String generateJWT(String userId, String uniqueId,String key) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BASE64_SECRET);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = null;
        try {
            builder = Jwts.builder().setHeader(headMap)
                    //加密后的openId
                    .claim("userId", AESUtil.encrypt(userId, key))
                    .claim("uniqueId", AESUtil.encrypt(uniqueId, key))
                    //Signature
                    .signWith(signatureAlgorithm, signingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加Token过期时间
        if (EXPIRES_SECOND >= 0) {
            long expMillis = nowTimeMillis + EXPIRES_SECOND;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate).setNotBefore(now);
        }
        return builder.compact();
    }


    /**
     * 生成不过期jwt
     *
     * @param jsonObject
     * @return
     */
    public static String generateEverJWT(JSONObject jsonObject) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BASE64_SECRET);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = null;
        try {
            builder = Jwts.builder().setHeader(headMap)
                    //加密后的openId
                    .claim("json", AESUtil.encrypt(jsonObject.toString(), DATA_KEY))
                    //Signature
                    .signWith(signatureAlgorithm, signingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.compact();
    }

    /**
     * 生成不过期jwt
     *
     * @param jsonObject
     * @return
     */
    public static String generateEverJWT(JSONObject jsonObject,String key) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BASE64_SECRET);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = null;
        try {
            builder = Jwts.builder().setHeader(headMap)
                    //加密后的openId
                    .claim("json", AESUtil.encrypt(jsonObject.toString(), key))
                    //Signature
                    .signWith(signatureAlgorithm, signingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.compact();
    }

    /**
     * 解析不过期的jwt
     *
     * @param jsonWebToken
     * @return
     */
    public static JSONObject validateEverLogin(String jsonWebToken) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        JSONObject resultObject = new JSONObject();
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("json"), DATA_KEY);
                resultObject = JSONUtil.parseObj(decryptUserId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    /**
     * 解析不过期的jwt
     *
     * @param jsonWebToken
     * @return
     */
    public static JSONObject validateEverLogin(String jsonWebToken,String key) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        JSONObject resultObject = new JSONObject();
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("json"), key);
                resultObject = JSONUtil.parseObj(decryptUserId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    /**
     * 解析JWT
     */
    private static Claims parseJWT(String jsonWebToken) {
        Claims claims = null;
        try {
            if (StringUtils.isNotBlank(jsonWebToken)) {
                //解析jwt
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(BASE64_SECRET))
                        .parseClaimsJws(jsonWebToken).getBody();
                return claims;
            } else {
                log.info("token为空！重新获取用户信息！");
            }
        } catch (Exception e) {
            log.error("token为空！可能因为token已经超时或非法token", e);
        }
        return null;
    }

    /**
     * 返回json字符串的demo:
     * {"freshToken":"A.B.C","userName":"Judy","userId":"123", "userAgent":"xxxx"}
     * freshToken-刷新后的jwt
     * userName-客户名称
     * userId-客户编号
     * userAgent-客户端浏览器信息
     */
    public static String validateLogin(String jsonWebToken) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("userId"), DATA_KEY);
            } catch (Exception e) {
                log.error("解析token获取userId异常", e);
            }
        }
        return decryptUserId;
    }

    /**
     * 返回json字符串的demo:
     * {"freshToken":"A.B.C","userName":"Judy","userId":"123", "userAgent":"xxxx"}
     * freshToken-刷新后的jwt
     * userName-客户名称
     * userId-客户编号
     * userAgent-客户端浏览器信息
     */
    public static String validateLogin(String jsonWebToken,String key) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("userId"), key);
            } catch (Exception e) {
                log.error("解析token获取userId异常", e);
            }
        }
        return decryptUserId;
    }


    public static String getUniqueIdByToken(String jsonWebToken) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("uniqueId"), DATA_KEY);
            } catch (Exception e) {
                log.error("解析token获取uniqueId异常", e);
            }
        }
        return decryptUserId;
    }

    public static String getUniqueIdByToken(String jsonWebToken,String key) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("uniqueId"), key);
            } catch (Exception e) {
                log.error("解析token获取uniqueId异常", e);
            }
        }
        return decryptUserId;
    }

    public static String getUserIdByToken(String jsonWebToken) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("userId"), DATA_KEY);
            } catch (Exception e) {
                log.error("解析token获取uniqueId异常", e);
            }
        }
        return decryptUserId;
    }

    public static String getUserIdByToken(String jsonWebToken,String key) {
        Claims claims = parseJWT(jsonWebToken);
        String decryptUserId = null;
        if (claims != null) {
            try {
                decryptUserId = AESUtil.decrypt((String) claims.get("userId"), key);
            } catch (Exception e) {
                log.error("解析token获取uniqueId异常", e);
            }
        }
        return decryptUserId;
    }

    public static void main(String[] args) {
        String token = JwtUtils.generateJWT("userId", "uniqueId");
        System.out.println(token);
        String uniqueIdByToken = getUniqueIdByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJ0TFcrU3J1TU4yaENZVVNGdmdEYlR3PT0iLCJ1bmlxdWVJZCI6IjVOaDBVS2pEVzNsNmxZaFk0NlNtQ2c9PSIsImV4cCI6MTY2MjgzNzg2OCwibmJmIjoxNjYxMTMyODM1fQ.KFDocmyIjn7FF5im--VUBnMd7P42h561bP6vVkRwuxE");
        System.out.println(uniqueIdByToken);
        System.out.println(StringUtils.isBlank(uniqueIdByToken));
    }

}
