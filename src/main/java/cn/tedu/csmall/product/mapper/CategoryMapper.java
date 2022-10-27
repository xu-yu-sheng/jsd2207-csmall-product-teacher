package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Category;
import cn.tedu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.CategoryStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理类别数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface CategoryMapper {

    /**
     * 插入类别数据
     *
     * @param category 类别数据
     * @return 受影响的行数
     */
    int insert(Category category);

    /**
     * 批量插入类别数据
     *
     * @param categories 类别列表
     * @return 受影响的行数
     */
    int insertBatch(List<Category> categories);

    /**
     * 根据id删除类别数据
     *
     * @param id 类别id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 根据若干个id批量删除类别数据
     *
     * @param ids 若干个类别id的数组
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 根据id修改类别数据
     *
     * @param category 封装了类别id和新数据的对象
     * @return 受影响的行数
     */
    int update(Category category);

    /**
     * 统计类别表中的数据的数量
     *
     * @return 类别表中的数据的数量
     */
    int count();

    /**
     * 根据类别名称统计当前表中类别数据的数量
     *
     * @param name 类别名称
     * @return 当前表中匹配名称的类别数据的数量
     */
    int countByName(String name);

    /**
     * 根据id查询类别详情
     *
     * @param id 类别id
     * @return 匹配的类别详情，如果没有匹配的数据，则返回null
     */
    CategoryStandardVO getStandardById(Long id);

    /**
     * 查询类别列表
     *
     * @return 类别列表
     */
    List<CategoryListItemVO> list();

}
