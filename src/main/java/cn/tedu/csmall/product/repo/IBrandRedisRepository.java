package cn.tedu.csmall.product.repo;

import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;

import java.util.List;

/**
 * 处理品牌缓存的数据访问接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IBrandRedisRepository {

    /**
     * 品牌数据项在Redis中的Key前缀
     */
    String BRAND_ITEM_KEY_PREFIX = "brand:item:";
    /**
     * 品牌列表在Redis中的Key
     */
    String BRAND_LIST_KEY = "brand:list";
    /**
     * 所有品牌数据项的Key
     */
    String BRAND_ITEM_KEYS_KEY = "brand:item-keys";

    /**
     * 向Redis中写入品牌数据
     *
     * @param brandStandardVO 品牌数据
     */
    void save(BrandStandardVO brandStandardVO);

    /**
     * 向Redis中写入品牌列表
     *
     * @param brands 品牌列表
     */
    void save(List<BrandListItemVO> brands);

    /**
     * 删除Redis中全部品牌数据，包括各品牌详情数据和品牌列表等
     *
     * @return 成功删除的数据的数量
     */
    Long deleteAll();

    /**
     * 从Redis中读取品牌数据
     *
     * @param id 品牌id
     * @return 匹配的品牌数据，如果没有匹配的数据，则返回null
     */
    BrandStandardVO get(Long id);

    /**
     * 从Redis中读取品牌列表
     *
     * @return 品牌列表
     */
    List<BrandListItemVO> list();

    /**
     * 从Redis中读取品牌列表
     *
     * @param start 读取数据的起始下标
     * @param end   读取数据的截止下标
     * @return 品牌列表
     */
    List<BrandListItemVO> list(long start, long end);

}
