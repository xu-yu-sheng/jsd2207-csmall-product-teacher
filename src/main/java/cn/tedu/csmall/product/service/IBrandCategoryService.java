package cn.tedu.csmall.product.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 品牌类别关联数据的业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IBrandCategoryService {

    /**
     * 添加品牌与类别的绑定
     *
     * @param brandId    品牌id
     * @param categoryId 类别id
     */
    void bind(Long brandId, Long categoryId);

    /**
     * 删除品牌与类别的绑定
     *
     * @param id 品牌与类别的绑定的数据id
     */
    void unbind(Long id);

}
