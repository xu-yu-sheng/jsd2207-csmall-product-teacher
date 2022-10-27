package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理属性模板数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface AttributeTemplateMapper {

    /**
     * 插入属性模板数据
     *
     * @param attributeTemplate 属性模板数据
     * @return 受影响的行数
     */
    int insert(AttributeTemplate attributeTemplate);

    /**
     * 批量插入属性模板数据
     *
     * @param albumList 若干个属性模板数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<AttributeTemplate> albumList);

    /**
     * 根据id删除属性模板数据
     *
     * @param id 属性模板id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除属性模板
     *
     * @param ids 需要删除的若干个属性模板的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 根据id修改属性模板数据详情
     *
     * @param attributeTemplate 封装了id与新数据的属性模板对象
     * @return 受影响的行数
     */
    int update(AttributeTemplate attributeTemplate);

    /**
     * 统计属性模板数据的数量
     *
     * @return 属性模板数据的数量
     */
    int count();

    /**
     * 根据属性模板名称统计当前表中属性模板数据的数量
     *
     * @param name 属性模板名称
     * @return 当前表中匹配名称的属性模板数据的数量
     */
    int countByName(String name);

    /**
     * 根据id查询属性模板标准信息
     *
     * @param id 属性模板id
     * @return 匹配的属性模板的标准信息，如果没有匹配的数据，则返回null
     */
    AttributeTemplateStandardVO getStandardById(Long id);

    /**
     * 查询属性模板列表
     *
     * @return 属性模板列表的集合
     */
    List<AttributeTemplateListItemVO> list();

}
