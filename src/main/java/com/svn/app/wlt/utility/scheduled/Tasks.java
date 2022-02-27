package com.svn.app.wlt.utility.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.svn.app.wlt.api.ExchangeInfoAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Tasks {
    @Autowired
    ExchangeInfoAPIService exchangeInfoAPIService;

    // every day at 00.00
    @Scheduled(cron="0 0 0 * * *", zone="Europe/Istanbul")
    private void updateExchangeInfoList(){
        System.out.println("SCHEDULE=======");
        try {
            exchangeInfoAPIService.updateExchangeInfoList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
