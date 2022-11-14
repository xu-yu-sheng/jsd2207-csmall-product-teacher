package cn.tedu.csmall.product.preload;

import cn.tedu.csmall.product.mapper.BrandMapper;
import cn.tedu.csmall.product.pojo.vo.BrandListItemVO;
import cn.tedu.csmall.product.repo.IBrandRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CachePreload implements ApplicationRunner {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private IBrandRedisRepository brandRedisRepository;

    public CachePreload() {
        log.debug("创建开机自动执行的组件对象：CachePreload");
    }

    // ApplicationRunner中的run()方法会在项目启动成功之后自动执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("CachePreload.run()");

        log.debug("准备删除Redis缓存中的品牌数据……");
        brandRedisRepository.deleteAll();
        log.debug("删除Redis缓存中的品牌数据，完成！");

        log.debug("准备从数据库中读取品牌列表……");
        List<BrandListItemVO> list = brandMapper.list();
        log.debug("从数据库中读取品牌列表，完成！");

        log.debug("准备将品牌列表写入到Redis缓存……");
        brandRedisRepository.save(list);
        log.debug("将品牌列表写入到Redis缓存，完成！");
    }

}
