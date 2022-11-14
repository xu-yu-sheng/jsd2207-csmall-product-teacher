package cn.tedu.csmall.product.repo;

import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;

import java.util.List;

public interface IBrandRedisRepository {

    String BRAND_ITEM_KEY_PREFIX = "brand:item:";

    String BRAND_LIST_KEY = "brand:list";

    void save(BrandStandardVO brandStandardVO);

    void save(List<BrandListItemVO> brands);

    BrandStandardVO get(Long id);

    List<BrandListItemVO> list();

    List<BrandListItemVO> list(long start, long end);

}
