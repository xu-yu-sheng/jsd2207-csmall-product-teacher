package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.CategoryUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    ICategoryService service;

    @Test
    void addNew() {
        CategoryAddNewDTO categoryAddNewDTO = new CategoryAddNewDTO();
        categoryAddNewDTO.setName("水果");
        // categoryAddNewDTO.setName("热带水果");
        // categoryAddNewDTO.setName("榴莲");
        categoryAddNewDTO.setParentId(0L);

        try {
            service.addNew(categoryAddNewDTO);
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
    void updateInfoById() {
        Long id = 1L;
        CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
        categoryUpdateDTO.setName("新-类别");
        categoryUpdateDTO.setKeywords("新-关键词");
        categoryUpdateDTO.setIcon("新-图标");
        categoryUpdateDTO.setSort(188);

        try {
            service.updateInfoById(id, categoryUpdateDTO);
            log.debug("测试修改数据成功！");
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

    @Test
    void setDisplay() {
        Long id = 1L;

        try {
            service.setDisplay(id);
            log.debug("测试显示数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void setHidden() {
        Long id = 1L;

        try {
            service.setHidden(id);
            log.debug("测试隐藏数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void getStandardById() {
        Long id = 1L;
        try {
            Object queryResult = service.getStandardById(id);
            log.debug("根据id【{}】查询完成，查询结果：{}", id, queryResult);
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void list() {
        List<?> list = service.list();
        log.debug("查询列表完成，列表中的数据的数量：{}", list.size());
        for (Object item : list) {
            log.debug("{}", item);
        }
    }
}
