package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Sku;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class SkuMapperTests {

    @Autowired
    SkuMapper mapper;

    @Transactional
    @Test
    void insert() {
        Sku sku = new Sku();
        sku.setId(1L);
        sku.setTitle("测试数据001");
        log.debug("插入数据之前，参数：{}", sku);
        int rows = mapper.insert(sku);
        log.debug("插入数据完成，受影响的行数：{}", rows);
        log.debug("插入数据之后，参数：{}", sku);
    }

    @Transactional
    @Test
    void insertBatch() {
        List<Sku> skuList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Sku sku = new Sku();
            sku.setId(i + 0L);
            sku.setTitle("批量插入测试数据" + i);
            skuList.add(sku);
        }

        int rows = mapper.insertBatch(skuList);
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
        Sku sku = new Sku();
        sku.setId(1L);
        sku.setTitle("新-测试数据001");

        int rows = mapper.update(sku);
        log.debug("更新完成，受影响的行数：{}", rows);
    }

    @Test
    void count() {
        int count = mapper.count();
        log.debug("统计完成，表中的数据的数量：{}", count);
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
