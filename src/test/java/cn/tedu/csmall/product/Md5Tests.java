package cn.tedu.csmall.product;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class Md5Tests {

    @Test
    public void encode() {
        String rawPassword = "123456";
        System.out.println("原文：" + rawPassword);
        for (int i = 0; i < 20; i++) {
            String salt = UUID.randomUUID().toString();
            String encodedPassword = DigestUtils
                    .md5DigestAsHex((rawPassword + salt).getBytes());
            System.out.println("密文：" + salt + encodedPassword);
        }
    }

//    @Test
//    public void bcryptEncode() {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String rawPassword = "123456";
//        System.out.println("原文：" + rawPassword);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 15; i++) {
//            String encodedPassword = passwordEncoder.encode(rawPassword);
//            System.out.println("密文：" + encodedPassword);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
//    }

}
