package com.svn.app.wlt.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svn.app.wlt.entity.BalanceLeg;
import com.svn.app.wlt.entity.Trade;
import com.svn.app.wlt.entity.TradeConversionPath;
import com.svn.app.wlt.entity.Wallet;
import com.svn.app.wlt.entity.dto.PathItem;
import com.svn.app.wlt.entity.dto.TradeDto;
import com.svn.app.wlt.repository.TradeConversionPathRepository;
import com.svn.app.wlt.repository.TradeRepository;
import com.svn.app.wlt.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TradeService {
    private TradeRepository tradeRepository;
    private WalletRepository walletRepository;
    private TradeConversionPathRepository tradeConversionPathRepository;
    private ExchangeInfoService exchangeInfoService;

    public TradeService(TradeRepository tradeRepository,
                        ExchangeInfoService exchangeInfoService,
                        WalletRepository walletRepository,
                        TradeConversionPathRepository tradeConversionPathRepository) {
        this.tradeRepository = tradeRepository;
        this.exchangeInfoService = exchangeInfoService;
        this.tradeConversionPathRepository = tradeConversionPathRepository;
        this.walletRepository = walletRepository;
    }

    public Trade findById(Long id){
        return tradeRepository.findById(id).get();
    }

    public Trade createDto(TradeDto tradeDto){
        Trade trade = convertDtoToEntity(tradeDto);
        trade.setId(null);
        return tradeRepository.save(trade);
    }

    public Trade updateDto(TradeDto tradeDto){
        Trade trade = convertDtoToEntity(tradeDto);
        return tradeRepository.save(trade);
    }

    public void deleteById(Long id){
        tradeRepository.deleteById(id);
    }

    public List<Trade> saveAll(List<Trade> tradeList){
        return tradeRepository.saveAll(tradeList);
    }

    public void autoUpdateBtcValue(List<Long> tradeIdList){
        List<TradeDto> tradeList = tradeRepository.findAllByIdAsDTO(tradeIdList);

        for (TradeDto trade : tradeList){
            //BTC PRICE AND PATH AT TIME

            List<PathItem> shortestPath = exchangeInfoService.shortestPath("BTC",trade.getQuoteAsset(),trade.getTime());
            String groupSymbol = "BTC"+trade.getQuoteAsset();
            Trade trade_ = new Trade();
            trade_.setId(trade.getId());

            BigDecimal btcValue = new BigDecimal(1);
            for(PathItem i :shortestPath) {
                TradeConversionPath tcp = new TradeConversionPath();
                tcp.setGroupSymbol(groupSymbol);
                tcp.setTrade(trade_);
                tcp.setPrice(i.getQuoteAssetPrice());
                tcp.setSymbol(i.getSymbol());
                tcp.setReverseDirection(i.getDirectionReverse());
                tcp.setRowNum(shortestPath.indexOf(i));
                tcp.setKlineTime(i.getKlineTime());

                if(tcp.getReverseDirection()){
                    btcValue = btcValue.divide(tcp.getPrice(),12, RoundingMode.HALF_UP);
                }else{
                    btcValue = btcValue.multiply(tcp.getPrice());
                }

                tradeConversionPathRepository.save(tcp);
            };

            trade.setBtcValue(btcValue);

            if(trade.getBaseAsset().equals(trade.getCommissionAsset())){
                trade.setCommissionBtcValue(btcValue);
            }else{
                List<PathItem> shortestPathForCommision = exchangeInfoService.shortestPath("BTC",trade.getCommissionAsset(),trade.getTime());
                String groupSymbolForCommision = "BTC"+trade.getCommissionAsset();

                BigDecimal btcValueForCommisionAsset = new BigDecimal(1);
                for(PathItem i :shortestPathForCommision) {
                    TradeConversionPath tcp = new TradeConversionPath();
                    tcp.setGroupSymbol(groupSymbolForCommision);
                    tcp.setTrade(trade_);
                    tcp.setPrice(i.getQuoteAssetPrice());
                    tcp.setSymbol(i.getSymbol());
                    tcp.setReverseDirection(i.getDirectionReverse());
                    tcp.setRowNum(shortestPathForCommision.indexOf(i));
                    tcp.setKlineTime(i.getKlineTime());

                    if(tcp.getReverseDirection()){
                        btcValueForCommisionAsset = btcValueForCommisionAsset.divide(tcp.getPrice(),12, RoundingMode.HALF_UP);
                    }else{
                        btcValueForCommisionAsset = btcValueForCommisionAsset.multiply(tcp.getPrice());
                    }

                    tradeConversionPathRepository.save(tcp);
                };
                trade.setCommissionBtcValue(btcValueForCommisionAsset);
            }


            // BTC USD PRICE AT TIME
            List<PathItem> shortestPathBtcUsdt = exchangeInfoService.shortestPath("BTC","USDT",trade.getTime());
            String groupSymbolBtcUsdt = "BTCUSDT";

            BigDecimal btcUsdtPrice = new BigDecimal(1);
            for(PathItem i :shortestPathBtcUsdt) {
                TradeConversionPath tcp = new TradeConversionPath();
                tcp.setGroupSymbol(groupSymbol);
                tcp.setTrade(trade_);
                tcp.setPrice(i.getQuoteAssetPrice());
                tcp.setSymbol(i.getSymbol());
                tcp.setReverseDirection(i.getDirectionReverse());
                tcp.setRowNum(shortestPathBtcUsdt.indexOf(i));
                tcp.setKlineTime(i.getKlineTime());

                if(tcp.getReverseDirection()){
                    btcUsdtPrice = btcUsdtPrice.divide(tcp.getPrice(),12, RoundingMode.HALF_UP);
                }else{
                    btcUsdtPrice = btcUsdtPrice.multiply(tcp.getPrice());
                }

                tradeConversionPathRepository.save(tcp);
            };
            trade.setBtcUsdPrice(btcUsdtPrice);
            updateDto(trade);

            // USER CURRENCY BTC PRICE AT TIME
            // TODO
        }
    }
    public List<TradeDto> findAllByBaseAssetAndLegIsNullAsDto(String asset){
        return tradeRepository.findAllByBaseAssetAndLegIsNullAsDto(asset);
    }

    private Trade convertDtoToEntity(TradeDto dto){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Trade trade = mapper.convertValue(dto, Trade.class);
        Wallet wallet = walletRepository.findById(dto.getWalletId()).get();
        trade.setWallet(wallet);
        if(dto.getBalanceLegId()!=null) {
            BalanceLeg leg = new BalanceLeg();
            leg.setId(dto.getBalanceLegId());
            trade.setBalanceLeg(leg);
        }
        return trade;
    }
}
