package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.BrandCategory;
import cn.tedu.csmall.product.pojo.vo.BrandCategoryListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandCategoryStandardVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理品牌与类别的关联数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface BrandCategoryMapper {

    /**
     * 插入品牌与类别的关联数据
     *
     * @param brandCategory 品牌与类别的关联数据
     * @return 受影响的行数
     */
    int insert(BrandCategory brandCategory);

    /**
     * 批量插入品牌与类别的关联数据
     *
     * @param brandCategoryList 若干个品牌与类别的关联数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<BrandCategory> brandCategoryList);

    /**
     * 根据id删除品牌与类别的关联数据
     *
     * @param id 品牌与类别的关联id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除品牌与类别的关联
     *
     * @param ids 需要删除的若干个品牌与类别的关联的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 更新品牌与类别的关联数据
     *
     * @param brandCategory 封装了品牌与类别的关联的id和需要更新的新数据的对象
     * @return 受影响的行数
     */
    int update(BrandCategory brandCategory);

    /**
     * 统计品牌与类别的关联数据的数量
     *
     * @return 品牌与类别的关联数据的数量
     */
    int count();

    /**
     * 根据品牌统计关联数据的数量
     *
     * @param brandId 类别id
     * @return 此类别关联的数据的数量
     */
    int countByBrand(Long brandId);

    /**
     * 根据类别统计关联数据的数量
     *
     * @param categoryId 类别id
     * @return 此类别关联的数据的数量
     */
    int countByCategory(Long categoryId);

    /**
     * 根据品牌与类别统计关联数据的数量
     *
     * @param brandId    品牌id
     * @param categoryId 类别id
     * @return 此属性模板关联的数据的数量
     */
    int countByBrandAndCategory(@Param("brandId") Long brandId, @Param("categoryId") Long categoryId);

    /**
     * 根据id查询品牌与类别的关联标准信息
     *
     * @param id 品牌与类别的关联id
     * @return 匹配的品牌与类别的关联的标准信息，如果没有匹配的数据，则返回null
     */
    BrandCategoryStandardVO getStandardById(Long id);

    /**
     * 查询品牌与类别的关联列表
     *
     * @return 品牌与类别的关联列表
     */
    List<BrandCategoryListItemVO> list();

}
