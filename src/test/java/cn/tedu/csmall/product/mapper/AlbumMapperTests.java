package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insert() {
        Album album = new Album();
        album.setName("测试相册002");
        album.setDescription("测试相册简介002");
        album.setSort(255);
        int rows = mapper.insert(album);
        System.out.println("插入相册数据完成，受影响的行数：" + rows);
    }

}
