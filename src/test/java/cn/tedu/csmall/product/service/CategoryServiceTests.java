package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    ICategoryService service;

    @Test
    void testAddNew() {
        CategoryAddNewDTO categoryAddNewDTO = new CategoryAddNewDTO();
        categoryAddNewDTO.setName("热带水果");
        categoryAddNewDTO.setParentId(25L);

        try {
            service.addNew(categoryAddNewDTO);
            log.debug("测试添加类别成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
