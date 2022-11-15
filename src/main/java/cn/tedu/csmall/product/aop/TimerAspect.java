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
