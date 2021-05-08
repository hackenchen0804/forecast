package com.hacken.forecast.util;

import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class Base64Convert {

    public String md5encode(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }

    public String md5decode(String strWithMD5) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(strWithMD5),"UTF-8");
    }
}
