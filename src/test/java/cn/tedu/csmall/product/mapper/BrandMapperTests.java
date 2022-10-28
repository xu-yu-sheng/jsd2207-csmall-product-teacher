package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Brand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class BrandMapperTests {

    @Autowired
    BrandMapper mapper;

    @Test
    void insert() {
        Brand brand = new Brand();
        brand.setName("测试品牌123");

        log.debug("插入数据之前，参数：{}", brand);
        int rows = mapper.insert(brand);
        log.debug("插入数据完成，受影响的行数：{}", rows);
        log.debug("插入数据之后，参数：{}", brand);
    }

    @Test
    void insertBatch() {
        List<Brand> brands = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Brand brand = new Brand();
            brand.setName("批量插入测试数据" + i);
            brands.add(brand);
        }

        int rows = mapper.insertBatch(brands);
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
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("新-测试数据001");

        int rows = mapper.update(brand);
        log.debug("更新完成，受影响的行数：{}", rows);
    }

    @Test
    void count() {
        int count = mapper.count();
        log.debug("统计完成，表中的数据的数量：{}", count);
    }

    @Test
    void countByName() {
        String name = "测试数据";
        int count = mapper.countByName(name);
        log.debug("根据名称【{}】统计数据的数量，结果：{}", name, count);
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
