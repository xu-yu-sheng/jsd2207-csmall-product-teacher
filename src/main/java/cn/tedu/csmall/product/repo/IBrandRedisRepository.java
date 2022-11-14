package cn.tedu.csmall.product.repo;

import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;

public interface IBrandRedisRepository {

    String BRAND_ITEM_KEY_PREFIX = "brand:item:";

    void save(BrandStandardVO brandStandardVO);

    BrandStandardVO get(Long id);

}
