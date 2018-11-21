package com.investor.cron;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.investor.database.MySQLClient;
import com.investor.dto.RuleDTO;
import com.investor.dto.TransDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LiveBoardClient {
    private static final Logger logger = LogManager.getLogger(LiveBoardClient.class);

    public static List<TransDTO> queryHNX() {
        List<TransDTO> list = null;
        try {
            String url = "http://banggia.cafef.vn/stockhandler.ashx?center=2";

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            // add request header
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (statusCode == 200) {
                list = new ArrayList<TransDTO>();
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = (JsonArray) parser.parse(result.toString().replaceAll("Time","tm"));
                for (int j = 0; j < jsonArray.size(); j++) {
                    TransDTO transDTO = gson.fromJson( jsonArray.get(j).getAsJsonObject(), TransDTO.class);
                    List<RuleDTO> listCodes = MySQLClient.queryCodes();
                    for(int k = 0; k < listCodes.size(); k ++){
                        RuleDTO ruleDTO = listCodes.get(k);
                        if(transDTO.getA().equalsIgnoreCase(ruleDTO.getCode())){
                            list.add(transDTO);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
        return list;
    }

    public static List<TransDTO> queryHOSE() {
        List<TransDTO> list = null;
        try {

            String url = "http://banggia.cafef.vn/stockhandler.ashx?center=1";

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            // add request header
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Response Code : "
                    + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (statusCode == 200) {
                list = new ArrayList<TransDTO>();
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = (JsonArray) parser.parse(result.toString().replaceAll("Time","tm"));
                for (int j = 0; j < jsonArray.size(); j++) {
                    TransDTO transDTO = gson.fromJson( jsonArray.get(j).getAsJsonObject(), TransDTO.class);
                    List<RuleDTO> listCodes = MySQLClient.queryCodes();
                    for(int k = 0; k < listCodes.size(); k ++){
                        RuleDTO ruleDTO = listCodes.get(k);
                        if(transDTO.getA().equalsIgnoreCase(ruleDTO.getCode())){
                            list.add(transDTO);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            logger.error(ex);
        }
        return list;
    }
}
