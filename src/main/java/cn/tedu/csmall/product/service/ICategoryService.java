package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;

/**
 * 类别业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface ICategoryService {

    /**
     * 添加类别
     *
     * @param categoryAddNewDTO 添加的类别对象
     */
    void addNew(CategoryAddNewDTO categoryAddNewDTO);

}
