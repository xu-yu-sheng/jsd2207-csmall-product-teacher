package cn.tedu.csmall.product.repo.repo.impl;

import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import cn.tedu.csmall.product.repo.IBrandRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

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
        String key = "brand:" + brandStandardVO.getId();
        redisTemplate.opsForValue().set(key, brandStandardVO);
    }

}
