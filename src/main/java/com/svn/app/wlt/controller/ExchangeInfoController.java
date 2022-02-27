package com.svn.app.wlt.controller;

import com.svn.app.wlt.entity.ExchangeInfo;
import com.svn.app.wlt.entity.dto.ExchangeDto;
import com.svn.app.wlt.entity.dto.PathItem;
import com.svn.app.wlt.service.ExchangeInfoService;
import com.svn.app.wlt.service.ExchangeService;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exchange-info")
public class ExchangeInfoController {

    private ExchangeInfoService exchangeInfoService;

    public ExchangeInfoController(ExchangeInfoService exchangeInfoService) {
        this.exchangeInfoService = exchangeInfoService;
    }

    @PostMapping("/by-base-assets")
    public List<ExchangeInfo> getByBaseAsset(@RequestBody List<String> baseAssetList){
        return exchangeInfoService.getByBaseAsset(baseAssetList);
    }
    @GetMapping("/find/{baseAsset}/{quoteAsset}/{time}")
    public List<PathItem> test(@PathVariable String baseAsset, @PathVariable String quoteAsset, @PathVariable Long time){
        return exchangeInfoService.shortestPath(baseAsset,quoteAsset,time);
    }

}
