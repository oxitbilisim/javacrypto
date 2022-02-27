package com.svn.app.wlt.api;

import com.svn.app.wlt.api.binance.domain.market.Candlestick;

import java.util.List;

public interface IExchangeService {


    List<Candlestick> getKlines(String symbol, String interval, Long startTime, Long endTime, Integer limit);
}
