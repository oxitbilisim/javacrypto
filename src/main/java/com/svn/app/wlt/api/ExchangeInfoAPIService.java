package com.svn.app.wlt.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svn.app.wlt.entity.ExchangeInfo;
import com.svn.app.wlt.entity.Parameter;
import com.svn.app.wlt.repository.ExchangeInfoRepository;
import com.svn.app.wlt.repository.ParameterRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeInfoAPIService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    ExchangeInfoRepository exchangeInfoRepository;

    public void updateExchangeInfoList() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        Parameter urlParam = parameterRepository.getApiUrls("BINANCE").stream().findFirst().orElseThrow();
        Parameter uriParam = parameterRepository.getApiUri("BINANCE_EXCHANGE_INFO").orElseThrow();
        String apiUrl = urlParam.getValue()+uriParam.getValue();

        String bodyJson = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class).getBody();
        JSONObject jo = new JSONObject(bodyJson);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<ExchangeInfo> exchangeInfoListNew = objectMapper.readValue(jo.getJSONArray("symbols").toString(), new TypeReference<List<ExchangeInfo>>(){});
        List<ExchangeInfo> exchangeInfoListExisting = exchangeInfoRepository.findAll();

        List<ExchangeInfo> doenstExistList = exchangeInfoListNew.stream().filter(i -> {
            Optional<ExchangeInfo> temp_ = exchangeInfoListExisting.stream().filter(j->j.getSymbol().equals(i.getSymbol())).findFirst();
            if(temp_.isPresent())
                return false;
            else
                return true;
        }).collect(Collectors.toList());
        exchangeInfoRepository.saveAll(doenstExistList);

        List<ExchangeInfo> changedList = new ArrayList<>();
        for (ExchangeInfo i:
        exchangeInfoListNew) {
            Optional<ExchangeInfo> temp_ = exchangeInfoListExisting.stream().filter(j->j.getSymbol().equals(i.getSymbol())).findFirst();
            if(temp_.isPresent()){
                ExchangeInfo temp = temp_.get();
                Boolean chached = false;
                if(i.getStatus()!=null && temp.getStatus()!=null && !i.getStatus().equals(temp.getStatus())) {
                    temp.setStatus(i.getStatus());
                    chached = true;
                }
                if(i.getBaseAssetPrecision()!=null && temp.getBaseAssetPrecision()!=null && !i.getBaseAssetPrecision().equals(temp.getBaseAssetPrecision())) {
                    temp.setBaseAssetPrecision(i.getBaseAssetPrecision());
                    chached = true;
                }
                if(i.getQuotePrecision()!=null && temp.getQuotePrecision()!=null && !i.getQuotePrecision().equals(temp.getQuotePrecision())) {
                    temp.setQuotePrecision(i.getQuotePrecision());
                    chached = true;
                }
                if(i.getQuoteAssetPrecision()!=null && temp.getQuoteAssetPrecision()!=null && !i.getQuoteAssetPrecision().equals(temp.getQuoteAssetPrecision())) {
                    temp.setQuoteAssetPrecision(i.getQuoteAssetPrecision());
                    chached = true;
                }
                if(i.getBaseCommissionPrecision()!=null && temp.getBaseCommissionPrecision()!=null && !i.getBaseCommissionPrecision().equals(temp.getBaseCommissionPrecision())) {
                    temp.setBaseCommissionPrecision(i.getBaseCommissionPrecision());
                    chached = true;
                }
                if(i.getQuoteCommissionPrecision()!=null && temp.getQuoteCommissionPrecision()!=null && !i.getQuoteCommissionPrecision().equals(temp.getQuoteCommissionPrecision())) {
                    temp.setQuoteCommissionPrecision(i.getQuoteCommissionPrecision());
                    chached = true;
                }

                if(chached)
                    changedList.add(temp);
            }

            exchangeInfoRepository.saveAll(changedList);
        }

    }
}
