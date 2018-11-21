package com.investor;

import com.investor.job.CheckTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Properties;

public class Main {
    public static Properties p = null;
    static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            logger.info("========Welcome to Application=========");
            p = new Properties();
            p.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
            CheckTrigger trigger = new CheckTrigger();
            trigger.init();
        } catch (Exception ex) {
            logger.error( ex);
        }
    }
}
