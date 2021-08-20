package com.guts.eggdemo.Thread;

import com.guts.eggdemo.util.HttpUtil;
import com.guts.eggdemo.util.RaspberryGpioUtil;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrongThread implements Runnable{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private int tick = 0;

    // 计时时间
    private int tickTime = 10;

    public void resartTick() {
        this.tick = 0;
    }

    @Override
    public void run() {
        if(tick < tickTime) {
            logger.info("Tick: {}; Date: {}", tick, new Date());
            tick ++;
        } else {
            RaspberryGpioUtil.setGpioTo1();
            HttpUtil.stopTicking();
        }
    }
}
