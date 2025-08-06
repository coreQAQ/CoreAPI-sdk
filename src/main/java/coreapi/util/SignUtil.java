package coreapi.util;

import cn.hutool.crypto.digest.DigestUtil;

public class SignUtil {

    public static String genSign(String str, String secretKey) {
         return DigestUtil.md5Hex(str + "." + secretKey);
    }

}