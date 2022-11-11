package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Spu;
import cn.tedu.csmall.product.pojo.vo.SpuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SpuStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理SPU数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface SpuMapper {

    /**
     * 插入SPU数据
     *
     * @param spu SPU数据
     * @return 受影响的行数
     */
    int insert(Spu spu);

    /**
     * 批量插入SPU数据
     *
     * @param spuList 若干个SPU数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<Spu> spuList);

    /**
     * 根据id删除SPU数据
     *
     * @param id SPUid
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除SPU
     *
     * @param ids 需要删除的若干个SPU的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 更新SPU数据
     *
     * @param spu 封装了SPU的id和需要更新的新数据的对象
     * @return 受影响的行数
     */
    int update(Spu spu);

    /**
     * 统计SPU数据的数量
     *
     * @return SPU数据的数量
     */
    int count();

    /**
     * 根据相册统计SPU数量
     *
     * @param albumId 相册ID
     * @return 关联了此相册的SPU数量
     */
    int countByAlbum(Long albumId);

    /**
     * 根据品牌统计SPU数量
     *
     * @param brandId 品牌ID
     * @return 关联了此品牌的SPU数量
     */
    int countByBrand(Long brandId);

    /**
     * 根据类别统计SPU数量
     *
     * @param categoryId 品牌ID
     * @return 关联了此类别的SPU数量
     */
    int countByCategory(Long categoryId);

    /**
     * 根据属性模板统计SPU数量
     *
     * @param attributeTemplateId 属性模板id
     * @return 此属性模板关联的数据的SPU数量
     */
    int countByAttributeTemplate(Long attributeTemplateId);

    /**
     * 根据id查询SPU标准信息
     *
     * @param id SPUid
     * @return 匹配的SPU的标准信息，如果没有匹配的数据，则返回null
     */
    SpuStandardVO getStandardById(Long id);

    /**
     * 查询SPU列表
     *
     * @return SPU列表
     */
    List<SpuListItemVO> list();

}
