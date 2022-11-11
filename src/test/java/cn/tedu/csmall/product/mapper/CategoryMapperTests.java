package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class CategoryMapperTests {

    @Autowired
    CategoryMapper mapper;

    @Test
    void insert() {
        Category category = new Category();
        category.setName("测试品牌001");
        category.setSort(255);
        log.debug("插入数据之前，参数：{}", category);
        int rows = mapper.insert(category);
        log.debug("插入数据完成，受影响的行数：{}", rows);
        log.debug("插入数据之后，参数：{}", category);
    }

    @Test
    void insertBatch() {
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Category category = new Category();
            category.setName("批量插入测试数据" + i);
            category.setSort(200);
            categories.add(category);
        }

        int rows = mapper.insertBatch(categories);
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
        Category category = new Category();
        category.setId(1L);
        category.setSort(188);

        int rows = mapper.update(category);
        log.debug("更新完成，受影响的行数：{}", rows);
    }

    @Test
    void count() {
        int count = mapper.count();
        log.debug("统计完成，表中的数据的数量：{}", count);
    }

    @Test
    void countByName() {
        String name = "某个新类别";
        int count = mapper.countByName(name);
        log.debug("根据名称【{}】统计数量完成，统计结果：{}", name, count);
    }

    @Test
    void countByNameAndNotId() {
        Long id = 1L;
        String name = "新-类别";
        int count = mapper.countByNameAndNotId(id, name);
        log.debug("根据名称【{}】且非ID【{}】统计数量完成，统计结果：{}", name, id, count);
    }

    @Test
    void countByParentId() {
        Long parentId = 1L;
        int count = mapper.countByParentId(parentId);
        log.debug("根据父级类别【{}】统计数量完成，统计结果：{}", parentId, count);
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
