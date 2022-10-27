package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AttributeTemplateServiceTests {

    @Autowired
    IAttributeTemplateService service;

    @Test
    void testAddNew() {
        AttributeTemplateAddNewDTO attributeTemplateAddNewDTO
                = new AttributeTemplateAddNewDTO();
        attributeTemplateAddNewDTO.setName("大米手机的属性模版");

        try {
            service.addNew(attributeTemplateAddNewDTO);
            log.debug("测试添加属性模板成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
