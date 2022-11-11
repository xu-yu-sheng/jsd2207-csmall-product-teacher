package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.AttributeTemplateUpdateInfoDTO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateStandardVO;

import java.util.List;

/**
 * 属性模板业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IAttributeTemplateService {

    /**
     * 添加属性模板
     *
     * @param attributeTemplateAddNewDTO 添加的属性模板对象
     */
    void addNew(AttributeTemplateAddNewDTO attributeTemplateAddNewDTO);

    /**
     * 删除商品属性模板
     *
     * @param id 被删除的商品属性模板的id
     */
    void delete(Long id);

    /**
     * 修改属性模板基本资料
     *
     * @param id                             属性模板id
     * @param attributeTemplateUpdateInfoDTO 封装了新基本资料的对象
     */
    void updateInfoById(Long id, AttributeTemplateUpdateInfoDTO attributeTemplateUpdateInfoDTO);

    /**
     * 根据id获取属性模板的标准信息
     *
     * @param id 属性模板id
     * @return 返回匹配的属性模板的标准信息，如果没有匹配的数据，将返回null
     */
    AttributeTemplateStandardVO getStandardById(Long id);

    /**
     * 查询属性模板列表
     *
     * @return 属性模板列表的集合
     */
    List<AttributeTemplateListItemVO> list();

}
