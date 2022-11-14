package cn.tedu.csmall.product.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheSchedule {

    public CacheSchedule() {
        log.debug("创建计划任务对象：CacheSchedule");
    }

    // fixedRate：执行频率，以毫秒值为单位
    @Scheduled(fixedRate = 5000)
    public void aaa() {
        log.debug("执行了计划任务…………");
    }

}
