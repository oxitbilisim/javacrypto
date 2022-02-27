package com.svn.app.wlt.service;

import com.svn.app.wlt.entity.ExchangeInfo;
import com.svn.app.wlt.entity.dto.ExchangeDto;
import com.svn.app.wlt.entity.dto.ExchangeInfoDto;
import com.svn.app.wlt.entity.dto.PathItem;
import com.svn.app.wlt.repository.ExchangeInfoRepository;
import com.svn.app.wlt.utility.ExchangePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeInfoService {
    private ExchangeInfoRepository exchangeInfoRepository;

    @Autowired
    private ApplicationContext applicationContext;

    public ExchangeInfoService(ExchangeInfoRepository exchangeInfoRepository) {
        this.exchangeInfoRepository = exchangeInfoRepository;
    }

    public List<ExchangeInfo> getByBaseAsset(List<String> baseAssets){
        return exchangeInfoRepository.getByBaseAsset(baseAssets);
    }

    public List<PathItem> shortestPath(String baseAsset, String quoteAsset,Long time){
        List<ExchangeInfoDto> ei = exchangeInfoRepository.findAllAsDto();
        ExchangePath ep = new ExchangePath(applicationContext,ei,baseAsset,quoteAsset,time);
        return ep.getFinalPath();
    }
}
