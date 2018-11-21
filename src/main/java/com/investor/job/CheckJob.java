package com.investor.job;

import com.investor.cron.LiveBoardClient;
import com.investor.database.MySQLClient;
import com.investor.dto.NotiDTO;
import com.investor.dto.RuleDTO;
import com.investor.dto.TransDTO;
import com.investor.mail.GmailClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckJob implements Job {
    private static final Logger logger = LogManager.getLogger(CheckJob.class);

    private boolean isRunning = false;

    public void execute(JobExecutionContext jobExecutionContext) {
        if (!isRunning) {
            isRunning = true;
            doJob();
            isRunning = false;
        } else {
            logger.info("=======Job is running========");
        }
    }

    private void doJob() {
        try {
            List hoseTxns = LiveBoardClient.queryHOSE();
            MySQLClient.insertTrans(hoseTxns);
            List hnxTxns = LiveBoardClient.queryHNX();
            MySQLClient.insertTrans(hnxTxns);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date dFrom = cal.getTime();
            Date dTo = new Date(dFrom.getTime() + 24 * 60 * 60 * 1000);
            List<RuleDTO> listRules = MySQLClient.queryRules();
            List<TransDTO> listMax = MySQLClient.queryMax(dFrom, dTo);
            List<TransDTO> listMin = MySQLClient.queryMin(dFrom, dTo);
            List<TransDTO> listLast = MySQLClient.queryLast();
            check(listRules, listMin, listMax, listLast, dFrom, dTo);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void check(List<RuleDTO> listRules, List<TransDTO> listMin, List<TransDTO> listMax, List<TransDTO> listLast, Date dFrom, Date dTo) {
        try {
            String bodyUp = "";
            String subjectUp = "";
            String bodyDown = "";
            String subjectDown = "";
            for (int j = 0; j < listRules.size(); j++) {
                RuleDTO ruleDTO = listRules.get(j);
                for (int k = 0; k < listLast.size(); k++) {
                    TransDTO transDTOLast = listLast.get(k);
                    for (int m = 0; m < listMin.size(); m++) {
                        TransDTO transDTOMin = listMin.get(m);
                        if ("up".equalsIgnoreCase(ruleDTO.getType())
                                && ruleDTO.getCode().equalsIgnoreCase(transDTOLast.getA())
                                && transDTOLast.getA().equalsIgnoreCase(transDTOMin.getA())) {
                            logger.info("last trans:"+transDTOLast.toString());
                            logger.info("min trans:"+transDTOMin.toString());
                            logger.info("ruleDTO trans:"+ruleDTO.toString());
                            logger.info("Code:" + ruleDTO.getCode());
                            boolean bVol = Integer.parseInt(transDTOLast.getN()) - Integer.parseInt(ruleDTO.getVolumn()) > 0;
                            logger.info("bVol:" + bVol);
                            float change = (Float.parseFloat(transDTOLast.getL()) - Float.parseFloat(transDTOMin.getL())) * Float.parseFloat(transDTOMin.getL()) / 100;
                            logger.info("Price change:" + String.format("%.2f", change) + "%");
                            List<NotiDTO> listNoti = MySQLClient.queryNoti(ruleDTO.getCode(), change - 0.5f, change + 0.5f, "up", dFrom, dTo);
                            logger.info("listNoti.size:" + listNoti.size());
                            if (bVol && change > 1 && listNoti.size() == 0) {
                                subjectUp += transDTOLast.getA() + " ";
                                bodyUp += "Code: " + transDTOLast.getA() + "<br>";
                                bodyUp += "State: +" + String.format("%.2f", Float.parseFloat(transDTOLast.getL()) - Float.parseFloat(transDTOMin.getL())) + "<br>";
                                bodyUp += "Change: " + String.format("%.2f", change) + "%" + "<br>";
                                bodyUp += "Min: " + transDTOMin.getL() + "<br>";
                                bodyUp += "Current: " + transDTOLast.getL() + "<br>";
                                bodyUp += "Vol: " + transDTOLast.getN() + "<br>";
                                bodyUp += "=====================" + "<br>";
                                MySQLClient.insertNoti(transDTOLast.getA(), change, subjectUp, bodyUp, "up", Integer.parseInt(transDTOLast.getN()));
                            }
                        }
                    }
                    for (int n = 0; n < listMax.size(); n++) {
                        TransDTO transDTOMax = listMax.get(n);
                        if ("down".equalsIgnoreCase(ruleDTO.getType())
                                && ruleDTO.getCode().equalsIgnoreCase(transDTOLast.getA())
                                && transDTOLast.getA().equalsIgnoreCase(transDTOMax.getA())) {
                            logger.info("last trans:"+transDTOLast.toString());
                            logger.info("max trans:"+transDTOMax.toString());
                            logger.info("ruleDTO trans:"+ruleDTO.toString());
                            logger.info("Code:" + ruleDTO.getCode());
                            boolean bVol = Integer.parseInt(transDTOLast.getN()) - Integer.parseInt(ruleDTO.getVolumn()) > 0;
                            logger.info("bVol:" + bVol);
                            float change = (Float.parseFloat(transDTOMax.getL()) - Float.parseFloat(transDTOLast.getL())) * Float.parseFloat(transDTOMax.getL()) / 100;
                            logger.info("Price change:" + String.format("%.2f", change) + "%");
                            List<NotiDTO> listNoti = MySQLClient.queryNoti(ruleDTO.getCode(), change - 0.5f, change + 0.5f, "up", dFrom, dTo);
                            logger.info("listNoti.size:" + listNoti.size());
                            if (bVol && change > 1 && listNoti.size() == 0) {
                                subjectDown += transDTOLast.getA() + " ";
                                bodyDown += "Code: " + transDTOLast.getA() + "<br>";
                                bodyDown += "State: -" + String.format("%.2f", Float.parseFloat(transDTOMax.getL()) - Float.parseFloat(transDTOLast.getL())) + "<br>";
                                bodyDown += "Change: " + String.format("%.2f", change) + "%" + "<br>";
                                bodyDown += "Max: " + transDTOMax.getL() + "<br>";
                                bodyDown += "Current: " + transDTOLast.getL() + "<br>";
                                bodyDown += "Vol: " + transDTOLast.getN() + "<br>";
                                bodyDown += "=====================" + "<br>";
                                MySQLClient.insertNoti(transDTOLast.getA(), change, subjectDown, bodyDown, "down", Integer.parseInt(transDTOLast.getN()));
                            }
                        }
                    }
                }
            }
            String subject = !"".equals(subjectDown) ? "Down " + subjectDown : "";
            subject += !"".equals(subjectUp) ? "Up " + subjectUp : "";
            String body = !"".equals(bodyDown) ? bodyDown : "";
            body += !"".equals(bodyUp) ? bodyUp : "";
            if (!"".equalsIgnoreCase(subject) && !"".equalsIgnoreCase(body)) {
                GmailClient.sendStandard(subject, body);
            } else {
                logger.info("Nothing !!!!");
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}
