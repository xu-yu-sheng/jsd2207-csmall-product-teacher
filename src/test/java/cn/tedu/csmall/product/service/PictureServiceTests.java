package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.PictureAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class PictureServiceTests {

    @Autowired
    IPictureService service;

    @Test
    void addNew() {
        PictureAddNewDTO pictureAddNewDTO = new PictureAddNewDTO();
        pictureAddNewDTO.setUrl("http://www.baidu.com/test.jpg");

        try {
            service.addNew(pictureAddNewDTO);
            log.debug("添加图片成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void delete() {
        Long id = 1L;

        try {
            service.delete(id);
            log.debug("根据id【{}】删除图片成功！", id);
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
