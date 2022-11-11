package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Spu;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class SpuMapperTests {

    @Autowired
    SpuMapper mapper;

    @Transactional
    @Test
    void insert() {
        Spu spu = new Spu();
        spu.setId(1L);
        spu.setTitle("测试数据001");
        log.debug("插入数据之前，参数：{}", spu);
        int rows = mapper.insert(spu);
        log.debug("插入数据完成，受影响的行数：{}", rows);
        log.debug("插入数据之后，参数：{}", spu);
    }

    @Transactional
    @Test
    void insertBatch() {
        List<Spu> spuList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Spu spu = new Spu();
            spu.setId(i + 0L);
            spu.setTitle("批量插入测试数据" + i);
            spuList.add(spu);
        }

        int rows = mapper.insertBatch(spuList);
        log.debug("批量插入完成，受影响的行数：{}", rows);
    }

    @Test
    void deleteById() {
        Long id = 1L;
        int rows = mapper.deleteById(id);
        log.debug("删除完成，受影响的行数：{}", rows);
    }

    @Test
    void deleteByIds() {
        Long[] ids = {1L, 3L, 5L};
        int rows = mapper.deleteByIds(ids);
        log.debug("批量删除完成，受影响的行数：{}", rows);
    }

    @Test
    void update() {
        Spu spu = new Spu();
        spu.setId(1L);
        spu.setTitle("新-测试数据001");

        int rows = mapper.update(spu);
        log.debug("更新完成，受影响的行数：{}", rows);
    }

    @Test
    void count() {
        int count = mapper.count();
        log.debug("统计完成，表中的数据的数量：{}", count);
    }

    @Test
    void countByAlbum() {
        Long albumId = 1L;
        int countByBrand = mapper.countByAlbum(albumId);
        log.debug("根据相册【{}】统计完成，数量：{}", albumId, countByBrand);
    }

    @Test
    void countByBrand() {
        Long brandId = 1L;
        int countByBrand = mapper.countByBrand(brandId);
        log.debug("根据品牌【{}】统计完成，数量：{}", brandId, countByBrand);
    }

    @Test
    void countByCategory() {
        Long categoryId = 1L;
        int count = mapper.countByCategory(categoryId);
        log.debug("根据类别【{}】统计关联数据的数量：{}", categoryId, count);
    }

    @Test
    void countByAttributeTemplate() {
        Long attributeTemplateId = 1L;
        int count = mapper.countByAttributeTemplate(attributeTemplateId);
        log.debug("根据属性模板【{}】统计关联数据的数量：{}", attributeTemplateId, count);
    }

    @Test
    void getStandardById() {
        Long id = 1L;
        Object queryResult = mapper.getStandardById(id);
        log.debug("根据id【{}】查询数据详情完成，查询结果：{}", id, queryResult);
    }

    @Test
    void list() {
        List<?> list = mapper.list();
        log.debug("查询列表完成，列表中的数据的数量：{}", list.size());
        for (Object item : list) {
            log.debug("{}", item);
        }
    }

}
