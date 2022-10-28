package cn.tedu.csmall.product.mapper;

import cn.tedu.csmall.product.pojo.entity.Sku;
import cn.tedu.csmall.product.pojo.vo.SkuListItemVO;
import cn.tedu.csmall.product.pojo.vo.SkuStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理SKU数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface SkuMapper {

    /**
     * 插入SKU数据
     *
     * @param sku SKU数据
     * @return 受影响的行数
     */
    int insert(Sku sku);

    /**
     * 批量插入SKU数据
     *
     * @param skuList 若干个SKU数据的集合
     * @return 受影响的行数
     */
    int insertBatch(List<Sku> skuList);

    /**
     * 根据id删除SKU数据
     *
     * @param id SKUid
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 批量删除SKU
     *
     * @param ids 需要删除的若干个SKU的id
     * @return 受影响的行数
     */
    int deleteByIds(Long[] ids);

    /**
     * 更新SKU数据
     *
     * @param sku 封装了SKU的id和需要更新的新数据的对象
     * @return 受影响的行数
     */
    int update(Sku sku);

    /**
     * 统计SKU数据的数量
     *
     * @return SKU数据的数量
     */
    int count();

    /**
     * 根据id查询SKU标准信息
     *
     * @param id SKUid
     * @return 匹配的SKU的标准信息，如果没有匹配的数据，则返回null
     */
    SkuStandardVO getStandardById(Long id);

    /**
     * 查询SKU列表
     *
     * @return SKU列表
     */
    List<SkuListItemVO> list();

}
