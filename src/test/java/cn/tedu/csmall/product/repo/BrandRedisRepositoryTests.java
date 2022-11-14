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
        brandStandardVO.setId(1L);
        brandStandardVO.setName("缓存品牌001");

        repository.save(brandStandardVO);
        log.debug("向Redis缓存中写入数据，完成！");
    }

    @Test
    void get() {
        Long id = 1L;
        BrandStandardVO queryResult = repository.get(id);
        log.debug("根据数据ID【{}】从Redis缓存中读取数据，结果：{}", id, queryResult);
    }

}
