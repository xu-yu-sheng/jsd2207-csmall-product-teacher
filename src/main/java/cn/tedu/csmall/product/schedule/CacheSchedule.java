package cn.tedu.csmall.product.schedule;

import lombok.extern.slf4j.Slf4j;
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

    public CacheSchedule() {
        log.debug("创建计划任务对象：CacheSchedule");
    }

    // fixedRate：执行频率，将按照上一次开始执行的时间来计算下一次的执行时间，以毫秒值为单位
    // fixedDelay：执行间隔时间，即上次执行结束后再过多久执行下一次，以毫秒值为单位
    @Scheduled(fixedRate = 5000)
    public void aaa() {
        log.debug("执行了计划任务…………");
    }

}
