# 102. 计划任务（续）

在根包下创建`schedule.CacheSchedule`类，在类上添加`@Component`注解，并在类中自定义计划任务方法：

```java
package cn.tedu.csmall.product.schedule;

import cn.tedu.csmall.product.service.IBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 处理缓存的计划任务类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Component
public class CacheSchedule {

    @Autowired
    private IBrandService brandService;

    public CacheSchedule() {
        log.debug("创建计划任务对象：CacheSchedule");
    }

    // 关于@Schedule注解的参数配置
    // fixedRate：执行频率，将按照上一次开始执行的时间来计算下一次的执行时间，以毫秒值为单位
    // fixedDelay：执行间隔时间，即上次执行结束后再过多久执行下一次，以毫秒值为单位
    // cron：使用1个字符串，其中包括6~7个值，各值之间使用1个空格进行分隔
    // >> 在cron的字符串中各值依次表示：秒 分 时 日 月 周（星期） [年]
    // >> 以上各值都可以使用通配符
    // >> 使用星号（*）表示任意值
    // >> 使用问号（?）表示不关心具体值，问号只能用于“日”和“周（星期）”
    // >> 例如："56 34 12 15 11 ? 2022"表示“2022年11月15日12:34:56，无视当天星期几”
    // >> 以上各值，可以使用“x/x”格式的值，例如：在分钟对应的位置设置“1/5”，则表示当分钟值为1时执行，且每间隔5分钟执行1次
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void rebuildBrandCache() {
        log.debug("开始执行【重建品牌缓存】计划任务…………");
        brandService.rebuildCache();
        log.debug("本次【重建品牌缓存】计划任务执行完成！");
    }

}
```

提示：以上计划任务需要在业务逻辑层补充“重建品牌缓存”的功能，在`IBrandService`中添加：

```java
/**
 * 重建品牌缓存
 */
void rebuildCache();
```

并在`BrandServiceImpl`中实现：

```java
@Override
public void rebuildCache() {
    log.debug("开始处理【重建品牌缓存】的业务，无参数");
    log.debug("准备删除Redis缓存中的品牌数据……");
    brandRedisRepository.deleteAll();
    log.debug("删除Redis缓存中的品牌数据，完成！");

    log.debug("准备从数据库中读取品牌列表……");
    List<BrandListItemVO> list = brandMapper.list();
    log.debug("从数据库中读取品牌列表，完成！");

    log.debug("准备将品牌列表写入到Redis缓存……");
    brandRedisRepository.save(list);
    log.debug("将品牌列表写入到Redis缓存，完成！");

    log.debug("准备将各品牌详情写入到Redis缓存……");
    for (BrandListItemVO brandListItemVO : list) {
        Long id = brandListItemVO.getId();
        BrandStandardVO brandStandardVO = brandMapper.getStandardById(id);
        brandRedisRepository.save(brandStandardVO);
    }
    log.debug("将各品牌详情写入到Redis缓存，完成！");
}
```

# 103. 手动更新缓存

由于在业务逻辑层已经实现“重建品牌缓存”的功能，在控制器中添加处理请求的方法，即可实现手动更新缓存：

```java
// http://localhost:9080/brands/cache/rebuild
@ApiOperation("重建品牌缓存")
@ApiOperationSupport(order = 600)
@PostMapping("/cache/rebuild")
public JsonResult<Void> rebuildCache() {
    log.debug("开始处理【重建品牌缓存】的请求，无参数");
    brandService.rebuildCache();
    return JsonResult.ok();
}
```

后续，客户端只需要提交请求，即可实现“重建品牌缓存”。

# 104. 按需加载缓存数据

假设当根据id获取品牌详情时，需要通过“按需加载缓存数据”的机制来实现缓存，可以将原业务调整为：

```java
@Override
public BrandStandardVO getStandardById(Long id) {
    log.debug("开始处理【根据id查询品牌详情】的业务，参数：{}", id);
    // 根据id从缓存中获取数据
    log.debug("将从Redis中获取相关数据");
    BrandStandardVO brand = brandRedisRepository.get(id);
    // 判断获取到的结果是否不为null
    if (brand != null) {
        // 是：直接返回
        log.debug("命中缓存，即将返回：{}", brand);
        return brand;
    }

    // 无缓存数据，从数据库中查找数据
    log.debug("未命中缓存，即将从数据库中查找数据");
    brand = brandMapper.getStandardById(id);
    // 判断查询到的结果是否为null
    if (brand == null) {
        // 是：抛出异常
        String message = "获取品牌详情失败，尝试访问的数据不存在！";
        log.warn(message);
        throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
    }

    // 将查询结果写入到缓存，并返回
    log.debug("从数据库查询到有效结果，将查询结果存入到Redis：{}", brand);
    brandRedisRepository.save(brand);
    log.debug("返回结果：{}", brand);
    return brand;
}
```













