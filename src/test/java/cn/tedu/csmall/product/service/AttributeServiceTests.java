package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.AttributeAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.AttributeUpdateInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    void updateInfoById() {
        Long id = 1L;
        AttributeUpdateInfoDTO attributeUpdateInfoDTO = new AttributeUpdateInfoDTO();
        attributeUpdateInfoDTO.setName("新-属性");
        attributeUpdateInfoDTO.setSort(188);

        try {
            service.updateInfoById(id, attributeUpdateInfoDTO);
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
    void listByTemplateId() {
        Long templateId = 1L;
        List<?> list = service.listByTemplateId(templateId);
        log.debug("根据属性模板ID【{}】查询列表完成，列表中的数据的数量：{}", templateId, list.size());
        for (Object item : list) {
            log.debug("{}", item);
        }
    }

}
