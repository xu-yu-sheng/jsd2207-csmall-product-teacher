package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BrandCategoryServiceTests {

    @Autowired
    IBrandCategoryService service;

    @Test
    void bind() {
        Long brandId = 1L;
        Long categoryId = 1L;

        try {
            service.bind(brandId, categoryId);
            log.debug("绑定品牌与类别的关联成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void unbind() {
        try {
            Long id = 1L;
            service.unbind(id);
            log.debug("解绑数据ID【{}】的品牌与类别成功！", id);
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
