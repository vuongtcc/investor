package com.investor.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;


public class AppScheduler {
    public static Scheduler scheduler;
    private static final Logger logger = LogManager.getLogger(AppScheduler.class);


    static {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    public static Scheduler getScheduler() {
        return scheduler;
    }
}
