package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Album;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insert() {
        Album album = new Album();
        album.setName("测试相册010");
        album.setDescription("测试相册简介010");
        album.setSort(255);
        log.debug("插入数据之前，参数：{}", album);
        int rows = mapper.insert(album);
        log.debug("插入相册数据完成，受影响的行数：{}", rows);
        log.debug("插入数据之后，参数：{}", album);
    }

    @Test
    void insertBatch() {
        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Album album = new Album();
            album.setName("批量插入测试相册" + i);
            album.setDescription("批量插入测试相册的简介" + i);
            album.setSort(200);
            albums.add(album);
        }

        int rows = mapper.insertBatch(albums);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }

    @Test
    void deleteById() {
        Long id = 6L;
        int rows = mapper.deleteById(id);
        log.debug("删除完成，受影响的行数：{}", rows);
    }

}
