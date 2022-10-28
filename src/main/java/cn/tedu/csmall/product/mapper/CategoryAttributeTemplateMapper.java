package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.CategoryAttributeTemplate;
import cn.tedu.csmall.product.pojo.vo.CategoryAttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryAttributeTemplateStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理类别与属性模板的关联数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface CategoryAttributeTemplateMapper {

    /**
     * 插入类别与属性模板的关联数据
     *
     * @param categoryAttributeTemplate 类别与属性模板的关联数据
     * @return 受影响的行数
     */
    int insert(CategoryAttributeTemplate categoryAttributeTemplate);

    /**
     * 批量插入类别与属性模板的关联数据
     *
     * @param categoryAttributeTemplateList 若干个类别与属性模板的关联数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<CategoryAttributeTemplate> categoryAttributeTemplateList);

    /**
     * 根据id删除类别与属性模板的关联数据
     *
     * @param id 类别与属性模板的关联id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除类别与属性模板的关联
     *
     * @param ids 需要删除的若干个类别与属性模板的关联的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 更新类别与属性模板的关联数据
     *
     * @param categoryAttributeTemplate 封装了类别与属性模板的关联的id和需要更新的新数据的对象
     * @return 受影响的行数
     */
    int update(CategoryAttributeTemplate categoryAttributeTemplate);

    /**
     * 统计类别与属性模板的关联数据的数量
     *
     * @return 类别与属性模板的关联数据的数量
     */
    int count();

    /**
     * 根据id查询类别与属性模板的关联标准信息
     *
     * @param id 类别与属性模板的关联id
     * @return 匹配的类别与属性模板的关联的标准信息，如果没有匹配的数据，则返回null
     */
    CategoryAttributeTemplateStandardVO getStandardById(Long id);

    /**
     * 查询类别与属性模板的关联的数据列表
     *
     * @return 类别与属性模板的关联的数据列表
     */
    List<CategoryAttributeTemplateListItemVO> list();

}
