package com.guts.eggdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaspberryGpioUtil {

    private static final Logger logger = LoggerFactory.getLogger("RaspberryGpioUtil");

    public static boolean setGpioTo0() {
        logger.info("GPIO SET TO 0");
        return true;
    }

    public static boolean setGpioTo1() {
        logger.info("GPIO SET TO 1");
        return true;
    }
}
