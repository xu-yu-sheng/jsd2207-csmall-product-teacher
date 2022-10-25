package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insert() {
        Album album = new Album();
        album.setName("测试相册008");
        album.setDescription("测试相册简介008");
        album.setSort(255);
        int rows = mapper.insert(album);
        log.info("插入相册数据完成，受影响的行数：" + rows);
    }

}
