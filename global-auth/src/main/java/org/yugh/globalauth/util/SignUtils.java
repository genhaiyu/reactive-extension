package org.yugh.globalauth.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignUtils {

    private static Log logger = LogFactory.getLog(SignUtils.class);

    private static final String STRING = "abcdefghijklmnopqrstuvwxyz";
    public static final String APPKEY = "appkey";
    public static final String EXPIRES = "expires";
    public static final String NONCE = "nonce";
    public static final String SIGNATURE = "signature";


    /**
     * 生成签名并且将签名加到参数表中
     *
     * @param params    业务参数列表信息
     * @param appKey
     * @param appSecret
     * @return
     */
    public static Map<String, Object> addSignature(Map<String, Object> params, String appKey, String appSecret) {
        String sign = generateSignature(params, appKey, appSecret);
        params.put(SIGNATURE, sign);
        return params;
    }

    /**
     * 生成签名并且将签名加到参数表中
     * （Base64编码后去除所有换行和回车，对应commons-codec 1.10版本处理逻辑）
     *
     * @param params    业务参数列表信息
     * @param appKey
     * @param appSecret
     * @return
     */
    public static Map<String, Object> addSignatureWithoutEnter(Map<String, Object> params, String appKey, String appSecret) {
        String sign = generateSignatureWithoutEnter(params, appKey, appSecret);
        params.put(SIGNATURE, sign);
        return params;
    }

    /**
     * 通过参数列表获取对应的签名值，验签时使用
     *
     * @param params 请求方传递过来的所有参数列表信息
     * @return
     */
    public static String getSignature(Map<String, Object> params, String appSecret) {
        if (params == null || params.size() == 0) {
            return null;
        }
        Map<String, Object> needParam = getNeedParam(params);
        String sortedParams = getEncodeString(needParam);
        return generateSignature(sortedParams, appSecret);
    }

    /**
     * 根据参数计算签名值
     *
     * @param params
     * @return
     */
    private static String generateSignature(Map<String, Object> params, String appKey, String appSecret) {
        if (params == null || params.size() == 0) {
            return null;
        }
        params.put(APPKEY, appKey);
        params.put(EXPIRES, getCurrentSecond());
        params.put(NONCE, generateRandomStr(6));
        String sortedParams = getEncodeString(params);
        return generateSignature(sortedParams, appSecret);
    }

    /**
     * 根据参数计算签名值
     * （Base64编码后去除所有换行和回车，对应commons-codec 1.10版本处理逻辑）
     *
     * @param params
     * @return
     */
    private static String generateSignatureWithoutEnter(Map<String, Object> params, String appKey, String appSecret) {
        if (params == null || params.size() == 0) {
            return null;
        }
        params.put(APPKEY, appKey);
        params.put(EXPIRES, getCurrentSecond());
        params.put(NONCE, generateRandomStr(6));
        String sortedParams = getEncodeString(params);
        return generateSignatureWithoutEnter(sortedParams, appSecret);
    }

    /**
     * 去掉param里面计算签名时不需要的参数
     *
     * @param param
     * @return
     */
    private static Map<String, Object> getNeedParam(Map<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            if (!SIGNATURE.equals(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * sha256取hash Base64编码
     *
     * @param params
     * @param secret
     * @return
     */
    private static String sha256HMACEncode(String params, String secret) {
        String result = "";
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] sha256HMACBytes = sha256HMAC.doFinal(params.getBytes());
            String hash = Base64.encodeBase64String(sha256HMACBytes);
            return hash;
        } catch (Exception e) {
            logger.error("sha256HMACEncode failed.", e);
        }
        return result;
    }


    /**
     * sha256取hash Base64编码
     * （Base64编码后去除所有换行和回车，对应commons-codec 1.10版本处理逻辑）
     *
     * @param params
     * @param secret
     * @return
     */
    private static String sha256HMACEncodeWithoutEnter(String params, String secret) {
        String result = "";
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] sha256HMACBytes = sha256HMAC.doFinal(params.getBytes());
            String hash = Base64.encodeBase64String(sha256HMACBytes).replaceAll("[\\s*\t\n\r]", "");
            return hash;
        } catch (Exception e) {
            logger.error("sha256HMACEncode failed.", e);
        }
        return result;
    }

    /**
     * 生成签名 取5-15共10位返回
     *
     * @param params
     * @param appSecret
     * @return
     */
    private static String generateSignature(String params, String appSecret) {
        String result = md5(sha256HMACEncode(params, appSecret)).substring(5, 15);
        logger.info("generateSignature result = " + result);
        return result;
    }

    /**
     * 生成签名 取5-15共10位返回
     * （Base64编码后去除所有换行和回车，对应commons-codec 1.10版本处理逻辑）
     *
     * @param params
     * @param appSecret
     * @return
     */
    private static String generateSignatureWithoutEnter(String params, String appSecret) {
        String result = md5(sha256HMACEncodeWithoutEnter(params, appSecret)).substring(5, 15);
        logger.info("generateSignature result = " + result);
        return result;
    }

    private static String md5(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] e = md.digest(value.getBytes());
            return byteToHexString(e);
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    private static String byteToHexString(byte[] salt) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < salt.length; i++) {
            String hex = Integer.toHexString(salt[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toLowerCase());
        }
        return hexString.toString();
    }

    // 通过参数集合map 组装成需要签名的字符串id=123&name=456
    private static String getEncodeString(Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        Iterator<String> it = params.keySet().iterator();
        ArrayList<String> al = new ArrayList<String>();
        while (it.hasNext()) {
            al.add(it.next());
        }
        // 字母升序排列
        Collections.sort(al);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < al.size(); i++) {
            String key = al.get(i);
            sb.append(key);
            sb.append("=");
            String item = null;
            try {
                Object value = params.get(key);
                item = URLEncoder.encode(value == null ? "" : value.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
            } catch (NullPointerException e) {
            }
            sb.append(item);
            sb.append("&");
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }

    // 获取len个随机字符串
    public static String generateRandomStr(int len) {
        StringBuffer sb = new StringBuffer();
        int count = len <= STRING.length() ? len : STRING.length();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            sb.append(STRING.charAt(random.nextInt(STRING.length() - 1)));
        }
        return sb.toString();
    }

    // 获取当前时间秒数+600S
    public static String getCurrentSecond() {
        return (System.currentTimeMillis() / 1000 + 600) + "";
    }

}