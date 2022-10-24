package cn.tedu.csmall.product;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

public class Md5Tests {

    @Test
    public void encode() {
        String rawPassword = "12345678";
        String encodedPassword = DigestUtils
                    .md5DigestAsHex(rawPassword.getBytes());
        System.out.println("原文：" + rawPassword);
        System.out.println("密文：" + encodedPassword);
    }

}
