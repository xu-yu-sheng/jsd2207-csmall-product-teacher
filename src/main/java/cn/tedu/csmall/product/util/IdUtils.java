package cn.tedu.csmall.product.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * ID工具类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public class IdUtils {

    private static DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");

    private static Random random = new Random();

    /**
     * 获取ID值
     *
     * @return ID值
     */
    public static Long getId() {
        // 【临时策略】使用 yyMMddHHmmssSSS 时间值并拼接在 [1000, 9999] 的随机数字
        LocalDateTime now = LocalDateTime.now(); // 2022-11-17T14:17:33.079
        String dateTimeString = dateTimeFormatter.format(now); // 20221117141959085
        int randomNumber = random.nextInt(8999) + 1000;
        String result = dateTimeString + randomNumber;
        long id = Long.valueOf(result);
        return id;
    }

}
