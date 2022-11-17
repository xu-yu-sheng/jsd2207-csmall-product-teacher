package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.SpuAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SpuServiceTests {

    @Autowired
    ISpuService service;

    @Test
    void addNew() {
        SpuAddNewDTO spuAddNewDTO = new SpuAddNewDTO();
        spuAddNewDTO.setBrandId(1L);
        spuAddNewDTO.setCategoryId(23L);
        spuAddNewDTO.setAlbumId(1L);
        spuAddNewDTO.setName("华为Mate88 10G手机");

        try {
            service.addNew(spuAddNewDTO);
            log.debug("测试添加数据成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
