package cn.tedu.csmall.product.repo;

import cn.tedu.csmall.product.pojo.vo.BrandStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BrandRedisRepositoryTests {

    @Autowired
    IBrandRedisRepository repository;

    @Test
    void save() {
        BrandStandardVO brandStandardVO = new BrandStandardVO();
        brandStandardVO.setId(100L);
        brandStandardVO.setName("缓存品牌100");

        repository.save(brandStandardVO);

        brandStandardVO = new BrandStandardVO();
        brandStandardVO.setId(300L);
        brandStandardVO.setName("缓存品牌300");

        repository.save(brandStandardVO);

        brandStandardVO = new BrandStandardVO();
        brandStandardVO.setId(400L);
        brandStandardVO.setName("缓存品牌400");

        repository.save(brandStandardVO);

        brandStandardVO = new BrandStandardVO();
        brandStandardVO.setId(500L);
        brandStandardVO.setName("缓存品牌500");

        repository.save(brandStandardVO);
    }

}
