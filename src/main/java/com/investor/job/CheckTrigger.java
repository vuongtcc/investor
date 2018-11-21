package com.investor.job;

import com.investor.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;


public class CheckTrigger {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public CheckTrigger() {

    }

    public void init() {
        try {
            logger.info("trigger expression:" + Main.p.getProperty("time.cron.schedule"));
            JobDetail jobDetail = JobBuilder.newJob(CheckJob.class)
                    .withIdentity("Check", "LiveBoard").build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("Check", "LiveBoard")
                    .withSchedule(CronScheduleBuilder.cronSchedule(Main.p.getProperty("time.cron.schedule")))
                    .build();
            AppScheduler.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (Exception ex) {
            logger.error(ex);

        }
    }


}

