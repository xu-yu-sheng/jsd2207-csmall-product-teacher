package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.AttributeTemplateAddNewDTO;

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

}
