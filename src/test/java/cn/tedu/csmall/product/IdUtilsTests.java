package cn.tedu.csmall.product;

import cn.tedu.csmall.product.util.IdUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class IdUtilsTests {

    @Test
    void getId() {
        long[] ids = new long[10];
        for (int i = 0; i < ids.length; i++) {
            long id = IdUtils.getId();
            ids[i] = id;
        }
        System.out.println(Arrays.toString(ids));
    }
}
