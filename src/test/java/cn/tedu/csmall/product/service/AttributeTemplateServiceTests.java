package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.AttributeTemplateUpdateInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AttributeTemplateServiceTests {

    @Autowired
    IAttributeTemplateService service;

    @Test
    void addNew() {
        AttributeTemplateAddNewDTO attributeTemplateAddNewDTO
                = new AttributeTemplateAddNewDTO();
        attributeTemplateAddNewDTO.setName("大米手机的属性模版");

        try {
            service.addNew(attributeTemplateAddNewDTO);
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
        AttributeTemplateUpdateInfoDTO attributeTemplateUpdateInfoDTO = new AttributeTemplateUpdateInfoDTO();
        attributeTemplateUpdateInfoDTO.setName("新-属性模板");
        attributeTemplateUpdateInfoDTO.setKeywords("新-关键词");
        attributeTemplateUpdateInfoDTO.setSort(188);

        try {
            service.updateInfoById(id, attributeTemplateUpdateInfoDTO);
            log.debug("测试修改数据成功！");
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
