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

    // 【AOP的核心概念】
    // 连接点（JoinPoint）：数据处理过程中的某个时间节点，可能是调用了方法，或抛出了异常
    // 切入点（PointCut）：选择一个或多个连接点的表达式
    // 通知（Advice）：在选择到的连接点执行的代码
    // 切面（Aspect）：是包含了切入点和通知的模块
    // ----------------------------------------------------------
    // 【通知注解】
    // @Before注解：表示“在……之前”，且方法应该是无参数的
    // @After注解：表示“在……之后”，无论是否抛出异常，或是否返回结果，都会执行，且方法应该是无参数的
    // @AfterReturning注解：表示“在返回结果之后”，且方法的参数是JoinPoint和返回值
    // @AfterThrowing注解：表示“在抛出异常之后”，且方法的参数是JoinPoint和异常对象
    // @Around注解：表示“包裹”，通常也称之为“环绕”，且方法的参数是ProceedingJoinPoint
    // ----------------------------------------------------------
    // @Around开始
    // try {
    //     @Before
    //     表达式匹配的方法
    //     @AfterReturning
    // } catch (Throwable e) {
    //     @AfterThrowing
    // } finally {
    //     @After
    // }
    // @Around结束
    // ----------------------------------------------------------
    // 注解中的execution内部配置表达式，以匹配上需要哪里执行切面代码
    // 表达式中，星号（*）是通配符，可匹配1次任意内容
    // 表达式中，2个连接的小数点（..）也是通配符，可匹配0~n次，只能用于包名和参数列表
    @Around("execution(* cn.tedu.csmall.product.service.*.*(..))")
    //                 ↑ 此星号表示需要匹配的方法的返回值类型
    //                   ↑ ---------- 根包 ----------- ↑
    //                                                  ↑ 类名
    //                                                    ↑ 方法名
    //                                                      ↑↑ 参数列表
    public Object timer(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("执行了TimeAspect中的方法……");

        log.debug("【{}】类型的对象调用了【{}】方法，参数值为【{}】",
                pjp.getTarget().getClass().getName(),
                pjp.getSignature().getName(),
                pjp.getArgs());

        long start = System.currentTimeMillis();
        // 注意：必须获取调用此方法的返回值，作为当前切面方法的返回值
        // 注意：必须抛出调用此方法的异常，不可以使用try...catch捕获并处理
        Object result = pjp.proceed(); // 相当于执行了匹配的方法，即业务方法
        long end = System.currentTimeMillis();

        log.debug("执行耗时：{}毫秒", end - start);

        return result;
    }

}
