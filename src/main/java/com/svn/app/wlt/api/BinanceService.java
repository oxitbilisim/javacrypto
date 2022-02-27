package com.svn.app.wlt.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.svn.app.wlt.api.binance.BinanceApiAsyncRestClient;
import com.svn.app.wlt.api.binance.BinanceApiClientFactory;
import com.svn.app.wlt.api.binance.BinanceApiRestClient;
import com.svn.app.wlt.api.binance.BinanceApiWebSocketClient;
import com.svn.app.wlt.api.binance.domain.market.Candlestick;
import com.svn.app.wlt.api.binance.domain.market.CandlestickInterval;
import com.svn.app.wlt.entity.Parameter;
import com.svn.app.wlt.repository.ParameterRepository;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class BinanceService implements IExchangeService{

    private ApplicationContext applicationContext;
    private RestTemplate restTemplate;
    private ParameterRepository parameterRepository;

    public BinanceService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.restTemplate = applicationContext.getBean(RestTemplate.class);
        this.parameterRepository = applicationContext.getBean(ParameterRepository.class);
    }

    @Override
    public List<Candlestick> getKlines(String symbol,String interval, Long startTime, Long endTime, Integer limit) {

        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiRestClient client = factory.newRestClient();//newAsyncRestClient();
        return client.getCandlestickBars(symbol, CandlestickInterval.ONE_MINUTE,limit,startTime,endTime);
    }

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }


    public void wstest() {

        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiWebSocketClient client = factory.newWebSocketClient();
        client.onCandlestickEvent("BTCUSDT",CandlestickInterval.ONE_MINUTE,response -> {

        });
    }
}
