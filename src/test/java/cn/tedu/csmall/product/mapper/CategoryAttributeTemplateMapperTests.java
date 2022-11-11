package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.CategoryAttributeTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class CategoryAttributeTemplateMapperTests {

    @Autowired
    CategoryAttributeTemplateMapper mapper;

    @Test
    void insert() {
        CategoryAttributeTemplate categoryAttributeTemplate = new CategoryAttributeTemplate();
        categoryAttributeTemplate.setCategoryId(1L);
        categoryAttributeTemplate.setAttributeTemplateId(1L);
        log.debug("插入数据之前，参数：{}", categoryAttributeTemplate);
        int rows = mapper.insert(categoryAttributeTemplate);
        log.debug("插入数据完成，受影响的行数：{}", rows);
        log.debug("插入数据之后，参数：{}", categoryAttributeTemplate);
    }

    @Test
    void insertBatch() {
        List<CategoryAttributeTemplate> categoryAttributeTemplateList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CategoryAttributeTemplate categoryAttributeTemplate = new CategoryAttributeTemplate();
            categoryAttributeTemplate.setCategoryId(i + 0L);
            categoryAttributeTemplate.setAttributeTemplateId(i + 0L);
            categoryAttributeTemplateList.add(categoryAttributeTemplate);
        }

        int rows = mapper.insertBatch(categoryAttributeTemplateList);
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
        CategoryAttributeTemplate categoryAttributeTemplate = new CategoryAttributeTemplate();
        categoryAttributeTemplate.setId(1L);
        categoryAttributeTemplate.setCategoryId(8L);
        categoryAttributeTemplate.setAttributeTemplateId(8L);

        int rows = mapper.update(categoryAttributeTemplate);
        log.debug("更新完成，受影响的行数：{}", rows);
    }

    @Test
    void count() {
        int count = mapper.count();
        log.debug("统计完成，表中的数据的数量：{}", count);
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
