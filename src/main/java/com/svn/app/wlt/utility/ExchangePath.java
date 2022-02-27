package com.svn.app.wlt.utility;

import com.google.common.collect.ImmutableMap;
import com.svn.app.wlt.api.ExchangeService;
import com.svn.app.wlt.api.binance.domain.market.Candlestick;
import com.svn.app.wlt.entity.dto.ExchangeInfoDto;
import com.svn.app.wlt.entity.dto.PathItem;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ExchangePath {
    List<ExchangeInfoDto> allData;
    String baseAsset;
    String quoteAsset;
    Long time;
    List<PathItem> finalPath = new ArrayList<>();
    List<String> visited = new ArrayList<>();

    ApplicationContext applicationContext;

    public ExchangePath(ApplicationContext applicationContext,List<ExchangeInfoDto> allData, String baseAsset, String quoteAsset,Long time) {
        this.allData = allData;
        this.time = time;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
        this.applicationContext = applicationContext;
        findRecursively(new ArrayList<>(),baseAsset);

    }

    private void findRecursively(List<PathItem> path, String baseAsset_){
        if(visited.contains(baseAsset_))
            return;

        visited.add(baseAsset_);
        Optional<ExchangeInfoDto> excOptional = allData.stream().filter(i ->
                (i.getBaseAsset().equals(baseAsset_) && i.getQuoteAsset().equals(quoteAsset)) || (i.getBaseAsset().equals(quoteAsset) && i.getQuoteAsset().equals(baseAsset_))
        ).findFirst();
        if(excOptional.isPresent()){
            ExchangeInfoDto exc = excOptional.get();
            Boolean directionReverse = false;
            if(exc.getQuoteAsset().equals(baseAsset_)) {
                directionReverse = true;
            }

            HashMap quotePrice = price(exc.getSymbol(),time);

            if(quotePrice.containsKey("price") && quotePrice.get("price")==null) return;

            path.add(new PathItem(exc.getSymbol(),exc.getBaseAsset(),exc.getQuoteAsset(),(BigDecimal) quotePrice.get("price"),directionReverse,(Long) quotePrice.get("time")));
            if(finalPath.size()==0)
                finalPath = path;
        }else{
            allData.stream().filter(j -> j.getBaseAsset().equals(baseAsset_) ||  j.getQuoteAsset().equals(baseAsset_)).forEach(exc ->{

                Boolean directionReverse = false;
                if(exc.getQuoteAsset().equals(baseAsset_)) {
                    directionReverse = true;
                }

                HashMap quotePrice = price(exc.getSymbol(),time);

                if(quotePrice.containsKey("price") && quotePrice.get("price")==null) return;
                path.add(new PathItem(exc.getSymbol(),exc.getBaseAsset(),exc.getQuoteAsset(),(BigDecimal) quotePrice.get("price"),directionReverse,(Long) quotePrice.get("time")));
                if(finalPath.size()==0)
                    findRecursively(new ArrayList<>(path),exc.getQuoteAsset());
            });
        }
    }

    private HashMap<String,Object> price(String symbol, Long time){
        ExchangeService s = new ExchangeService(applicationContext, Types.Exchange.BINANCE);
        List<Candlestick> r = s.instance().getKlines(symbol,"",time-30000L,time+30000L,1);
        if(r.size()>0){
            Candlestick c = r.get(0);
            BigDecimal high = new BigDecimal(c.getHigh());
            BigDecimal low = new BigDecimal(c.getLow());
            BigDecimal avgPrice = (high.add(low)).divide(new BigDecimal(2));
            return new HashMap<>(ImmutableMap.of("price",avgPrice,"time",c.getOpenTime()));
        }else{
            return null;
        }
    }


    public List<PathItem> getFinalPath() {
        return finalPath;
    }
}
