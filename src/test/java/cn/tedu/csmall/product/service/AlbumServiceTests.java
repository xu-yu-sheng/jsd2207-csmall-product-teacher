package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.ex.ServiceException;
import cn.tedu.csmall.product.pojo.dto.AlbumAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AlbumServiceTests {

    @Autowired
    IAlbumService service;

    @Test
    void addNew() {
        AlbumAddNewDTO albumAddNewDTO = new AlbumAddNewDTO();
        albumAddNewDTO.setName("测试数据1");
        albumAddNewDTO.setDescription("测试数据的简介1");
        albumAddNewDTO.setSort(100);

        try {
            service.addNew(albumAddNewDTO);
            log.debug("测试添加数据成功！");
        } catch (ServiceException e) {
            log.debug("测试添加数据失败！");
        }
    }

}
