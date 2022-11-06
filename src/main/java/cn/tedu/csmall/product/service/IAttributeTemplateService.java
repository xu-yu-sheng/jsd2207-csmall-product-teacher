package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.csmall.product.pojo.vo.AttributeTemplateListItemVO;

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
     * 查询属性模板列表
     *
     * @return 属性模板列表的集合
     */
    List<AttributeTemplateListItemVO> list();

}
