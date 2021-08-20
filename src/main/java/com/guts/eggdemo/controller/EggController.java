package com.guts.eggdemo.controller;

import com.guts.eggdemo.Thread.WrongThread;
import com.guts.eggdemo.util.RaspberryGpioUtil;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EggController {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ScheduledFuture<?> future;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    private WrongThread wrongThread;

    /**
     * 吃机制则开始计时，如果重复吃则刷新计时时间
     * @return 开始计时字符串
     */
    @GetMapping("/wrong")
    public String wrong() {
        if(wrongThread == null) {
            RaspberryGpioUtil.setGpioTo0();
            logger.info("Get wrong, start ticking. Time {}", new Date());
            wrongThread = new WrongThread();
            future = threadPoolTaskScheduler.schedule(wrongThread, new CronTrigger("* * * * * ?"));
        } else {
            logger.info("Ticking restart. Time {}", new Date());
            wrongThread.resartTick();
        }
        return "Start ticking succeed";
    }

    /**
     * 计时完毕
     * @return 计时完毕字符串
     */
    @GetMapping("/stop")
    public String stop() {
        if(future != null) {
            logger.info("Ticking timeout. {}", new Date());
            future.cancel(true);
            wrongThread = null;
        }
        return "Stop ticking succeed";
    }

    /**
     * 死亡时则停止震动，同时停止计时
     * @return 死亡状态判定字符串
     */
    @GetMapping("/dead")
    public String dead() {
        if(future != null) {
            future.cancel(true);
        }
        if(wrongThread != null) {
            wrongThread = null;
        }
        RaspberryGpioUtil.setGpioTo0();
        return "Dead, stop tick and stop shaking";
    }

    /**
     * 复活，开始震动
     * @return 返回复活判定字符串
     */
    @GetMapping("/relive")
    public String releve() {
        RaspberryGpioUtil.setGpioTo1();
        return "Relive succeed, start shaking";
    }
}
