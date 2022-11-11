package cn.tedu.csmall.product.service;

import cn.tedu.csmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.product.pojo.dto.CategoryUpdateDTO;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 根据id删除类别
     *
     * @param id 被删除的类别的id
     */
    void delete(Long id);

    /**
     * 根据类别id，修改类别详情
     *
     * @param id                类别id
     * @param categoryUpdateDTO 新的类别数据
     */
    void updateInfoById(Long id, CategoryUpdateDTO categoryUpdateDTO);

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

    /**
     * 根据id获取类别的标准信息
     *
     * @param id 类别id
     * @return 返回匹配的类别的标准信息，如果没有匹配的数据，将返回null
     */
    CategoryStandardVO getStandardById(Long id);

    /**
     * 查询类别列表
     *
     * @return 类别列表
     */
    List<CategoryListItemVO> list();

}
