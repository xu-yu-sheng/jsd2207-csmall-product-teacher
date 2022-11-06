package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.AttributeAddNewDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 属性业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IAttributeService {

    /**
     * 添加属性
     *
     * @param attributeAddNewDTO 添加的属性对象
     */
    void addNew(AttributeAddNewDTO attributeAddNewDTO);

}
