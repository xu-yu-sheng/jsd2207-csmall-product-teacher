package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Attribute;
import cn.tedu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeStandardVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理属性数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface AttributeMapper {

    /**
     * 插入属性数据
     *
     * @param attribute 属性数据
     * @return 受影响的行数
     */
    int insert(Attribute attribute);

    /**
     * 批量插入属性数据
     *
     * @param attributeList 若干个属性数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<Attribute> attributeList);

    /**
     * 根据id删除属性数据
     *
     * @param id 属性id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除属性
     *
     * @param ids 需要删除的若干个属性的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 更新属性数据
     *
     * @param attribute 封装了属性的id和需要更新的新数据的对象
     * @return 受影响的行数
     */
    int update(Attribute attribute);

    /**
     * 统计属性数据的数量
     *
     * @return 属性数据的数量
     */
    int count();

    /**
     * 根据属性模板统计属性数据的数量
     *
     * @param templateId 属性模板id
     * @return 此属性模板中属性数据的数量
     */
    int countByTemplateId(Long templateId);

    /**
     * 根据属性名称和属性模板统计当前表中属性数据的数量
     *
     * @param name       属性名称
     * @param templateId 属性模板id
     * @return 当前表中匹配名称的属性数据的数量
     */
    int countByNameAndTemplate(@Param("name") String name, @Param("templateId") Long templateId);

    /**
     * 统计当前表中非此属性id的匹配名称、匹配属性模板的属性数据的数量
     *
     * @param id 当前属性id
     * @param name 属性名称
     * @param templateId 属性模板id
     * @return 当前表中非此属性id的匹配名称、匹配属性模板的属性数据的数量
     */
    int countByNameAndTemplateAndNotId(@Param("id") Long id,
                                       @Param("name") String name,
                                       @Param("templateId") Long templateId);

    /**
     * 根据id查询属性标准信息
     *
     * @param id 属性id
     * @return 匹配的属性的标准信息，如果没有匹配的数据，则返回null
     */
    AttributeStandardVO getStandardById(Long id);

    /**
     * 查询属性列表
     *
     * @return 属性列表
     */
    List<AttributeListItemVO> list();

    /**
     * 根据属性模板id查询属性列表
     *
     * @param templateId 属性模板id
     * @return 属性列表的集合
     */
    List<AttributeListItemVO> listByTemplateId(Long templateId);

}
