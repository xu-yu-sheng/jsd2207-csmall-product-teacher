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

# 105. Spring AOP

AOP：面向切面的编程。

注意：AOP并不是Spring框架特有的技术，只是Spring框架很好的支持了AOP。

在项目中，数据的处理流程大致是：

```
添加品牌：请求 ------> Controller ------> Service ------> Mapper ------> DB

添加类别：请求 ------> Controller ------> Service ------> Mapper ------> DB

删除品牌：请求 ------> Controller ------> Service ------> Mapper ------> DB
```

其实，各请求提交到服务器端后，数据的处理流程是相对固定的！

假设存在某个需求，无论是添加品牌、添加类别、删除品牌，或其它请求的处理，都需要在Service组件中执行相同的任务，应该如何处理？

例如，假设需要实现“统计所有Service中的业务方法的执行耗时”，首先，需要添加依赖项：

```xml
<!-- Spring Boot AOP的依赖项 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

然后，在根包下创建`aop.TimerAspect`类，作为切面类，必须添加`@Aspect`注解，由于是通过Spring来实现AOP，所以，此类还应该交由Spring管理，它应该是个组件类，则再补充添加`@Component`注解，并在类中自定义方法，且通过注解来配置方法何时执行：

```java
package cn.tedu.csmall.product.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimerAspect {

    public TimerAspect() {
        log.debug("创建切面对象：TimerAspect");
    }

    // @Around注解表示“包裹”，通常也称之为“环绕”
    // @Around注解中的execution内部配置表达式，以匹配上需要哪里执行切面代码
    // 表达式中，星号（*）是通配符，可匹配1次任意内容
    // 表达式中，2个连接的小数点（..）也是通配符，可匹配0~n次，只能用于包名和参数列表
    @Around("execution(* cn.tedu.csmall.product.service.*.*(..))")
    //                 ↑ 此星号表示需要匹配的方法的返回值类型
    //                   ↑ ---------- 根包 ----------- ↑
    //                                                  ↑ 类名
    //                                                    ↑ 方法名
    //                                                      ↑↑ 参数列表
    public Object a(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        // 注意：必须获取调用此方法的返回值，作为当前切面方法的返回值
        Object result = pjp.proceed(); // 相当于执行了匹配的方法，即业务方法

        long end = System.currentTimeMillis();
        log.debug("执行耗时：{}毫秒", end - start);

        return result;
    }

}
```













