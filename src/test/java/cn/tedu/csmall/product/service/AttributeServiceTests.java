package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.AttributeAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AttributeServiceTests {

    @Autowired
    IAttributeService service;

    @Test
    void addNew() {
        AttributeAddNewDTO attributeAddNewDTO = new AttributeAddNewDTO();
        attributeAddNewDTO.setName("大米手机的颜色属性");

        try {
            service.addNew(attributeAddNewDTO);
            log.debug("测试添加数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
