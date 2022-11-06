package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类别业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface ICategoryService {

    /**
     * 添加类别
     *
     * @param categoryAddNewDTO 添加的类别对象
     */
    void addNew(CategoryAddNewDTO categoryAddNewDTO);

    /**
     * 启用类别
     *
     * @param id 尝试启用的类别的id
     */
    void setEnable(Long id);

    /**
     * 禁用类别
     *
     * @param id 尝试禁用的类别的id
     */
    void setDisable(Long id);

    /**
     * 显示类别
     *
     * @param id 尝试显示的类别的id
     */
    void setDisplay(Long id);

    /**
     * 隐藏类别
     *
     * @param id 尝试隐藏的类别的id
     */
    void setHidden(Long id);

}
