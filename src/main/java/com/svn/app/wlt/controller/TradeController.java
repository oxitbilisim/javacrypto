package com.svn.app.wlt.controller;

import com.svn.app.wlt.service.BalanceService;
import com.svn.app.wlt.service.ExchangeInfoService;
import com.svn.app.wlt.service.TradeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {

    private TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/auto-update-btc-value")
    public void getPriceAsBTCAndUSD(@RequestBody List<Long> tradeIds){
        tradeService.autoUpdateBtcValue(tradeIds);
    }


}
