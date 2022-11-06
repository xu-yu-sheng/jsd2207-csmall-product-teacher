package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.BrandAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BrandServiceTests {

    @Autowired
    IBrandService service;

    @Test
    void addNew() {
        BrandAddNewDTO brandAddNewDTO = new BrandAddNewDTO();
        brandAddNewDTO.setName("海尔");

        try {
            service.addNew(brandAddNewDTO);
            log.debug("测试添加数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void delete() {
        Long id = 1L;

        try {
            service.delete(id);
            log.debug("测试删除数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void setEnable() {
        Long id = 1L;

        try {
            service.setEnable(id);
            log.debug("测试启用数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void setDisable() {
        Long id = 1L;

        try {
            service.setDisable(id);
            log.debug("测试禁用数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
