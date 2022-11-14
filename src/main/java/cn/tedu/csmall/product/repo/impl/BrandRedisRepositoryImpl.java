package cn.tedu.csmall.product.repo.impl;

import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.repo.IBrandRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class BrandRedisRepositoryImpl implements IBrandRedisRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public BrandRedisRepositoryImpl() {
        log.debug("创建处理缓存的数据访问对象：BrandRedisRepositoryImpl");
    }

    @Override
    public void save(BrandStandardVO brandStandardVO) {
        String key = BRAND_ITEM_KEY_PREFIX + brandStandardVO.getId();
        redisTemplate.opsForSet().add(BRAND_ITEM_KEYS_KEY, key);
        redisTemplate.opsForValue().set(key, brandStandardVO);
    }

    @Override
    public void save(List<BrandListItemVO> brands) {
        String key = BRAND_LIST_KEY;
        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        for (BrandListItemVO brand : brands) {
            ops.rightPush(key, brand);
        }
    }

    @Override
    public Long deleteAll() {
        // 获取到所有item的key
        Set<Serializable> members = redisTemplate
                .opsForSet().members(BRAND_ITEM_KEYS_KEY);
        Set<String> keys = new HashSet<>();
        for (Serializable member : members) {
            keys.add((String) member);
        }
        // 将List和保存Key的Set的Key也添加到集合中
        keys.add(BRAND_LIST_KEY);
        keys.add(BRAND_ITEM_KEYS_KEY);
        return redisTemplate.delete(keys);
    }

    @Override
    public BrandStandardVO get(Long id) {
        Serializable serializable = redisTemplate
                .opsForValue().get(BRAND_ITEM_KEY_PREFIX + id);
        BrandStandardVO brandStandardVO = null;
        if (serializable != null) {
            if (serializable instanceof BrandStandardVO) {
                brandStandardVO = (BrandStandardVO) serializable;
            }
        }
        return brandStandardVO;
    }

    @Override
    public List<BrandListItemVO> list() {
        long start = 0;
        long end = -1;
        return list(start, end);
    }

    @Override
    public List<BrandListItemVO> list(long start, long end) {
        String key = BRAND_LIST_KEY;
        ListOperations<String, Serializable> ops = redisTemplate.opsForList();
        List<Serializable> list = ops.range(key, start, end);
        List<BrandListItemVO> brands = new ArrayList<>();
        for (Serializable item : list) {
            brands.add((BrandListItemVO) item);
        }
        return brands;
    }

}
