package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.BrandAddNewDTO;

/**
 * 品牌业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IBrandService {

    /**
     * 添加品牌
     *
     * @param brandAddNewDTO 品牌数据
     */
    void addNew(BrandAddNewDTO brandAddNewDTO);

    /**
     * 根据品牌id，删除品牌数据
     *
     * @param id 尝试删除的品牌的id
     */
    void delete(Long id);

}
