package com.svn.app.wlt.service;

import com.svn.app.wlt.entity.Balance;
import com.svn.app.wlt.entity.BalanceLeg;
import com.svn.app.wlt.entity.Trade;
import com.svn.app.wlt.entity.dto.TradeDto;
import com.svn.app.wlt.repository.BalanceLegRepository;
import com.svn.app.wlt.repository.BalanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class BalanceLegService {
    private BalanceLegRepository balanceLegRepository;
    private TradeService tradeService;

    public BalanceLegService(BalanceLegRepository balanceLegRepository,
                             TradeService tradeService) {
        this.balanceLegRepository = balanceLegRepository;
        this.tradeService = tradeService;
    }

    public BalanceLeg findById(Long id){
        return balanceLegRepository.findById(id).get();
    }

    public BalanceLeg create(BalanceLeg balanceLeg){
        balanceLeg.setId(null);
        return balanceLegRepository.save(balanceLeg);
    }

    public BalanceLeg update(BalanceLeg balanceLeg){
        return balanceLegRepository.save(balanceLeg);
    }

    public void deleteById(Long id){
        balanceLegRepository.deleteById(id);
    }

    public List<BalanceLeg> saveAll(List<BalanceLeg> balanceLegList){
        return balanceLegRepository.saveAll(balanceLegList);
    }

    public BalanceLeg findByBalanceIdAndIsOpenTrue(String asset){
        return balanceLegRepository.findByBalanceIdAndIsOpenTrue(asset);
    }

    public void calculateLegPNL(Balance balance,List<TradeDto> tradeDtoList){
        BalanceLeg leg= findByBalanceIdAndIsOpenTrue(balance.getAsset());
        // OPEN LEG
        BigDecimal qty = new BigDecimal(0);
        BigDecimal cost = new BigDecimal(0);

        BigDecimal unitCostPriceAsBtc = new BigDecimal(0);
        BigDecimal buyValueAsBtc = new BigDecimal(0);
        BigDecimal sellValueAsBtc = new BigDecimal(0);

        if(leg == null){
            leg = new BalanceLeg();
            leg.setBalance(balance);
            leg.setIsOpen(true);

            leg.setQty(new BigDecimal(0));
            leg.setCostAsBtc(new BigDecimal(0));
            leg.setRealizedBuyValue(new BigDecimal(0));
            leg.setRealizedSellValue(new BigDecimal(0));
            leg = balanceLegRepository.save(leg);
        }else{
            qty = leg.getQty();
            cost = leg.getCostAsBtc();
        }

        Long endTime = null;
        for (TradeDto trade: tradeDtoList) {
            /*
            System.out.println("===================");
            System.out.println("ID:"+ trade.getId());
            System.out.println("ID:"+ trade.getId());
             */
            endTime = trade.getTime();
            if(leg.getStartTime()==null){
                leg.setStartTime(trade.getTime());
            }

            if(trade.getIsBuyer()){
                // deposit is also ISBUYER
                qty = qty.add(trade.getQty());
                cost = cost.add(trade.getQuoteQty().divide(trade.getBtcValue(),12, RoundingMode.HALF_UP));
                /*
                System.out.println("btcValue : "+trade.getBtcValue());
                System.out.println("qty : "+trade.getQty());
                System.out.println("cost : "+trade.getQuoteQty());

                 */
            }else{

                unitCostPriceAsBtc = cost.divide(qty,12, RoundingMode.HALF_UP);
                sellValueAsBtc = trade.getQuoteQty().divide(trade.getBtcValue(),12, RoundingMode.HALF_UP);
                buyValueAsBtc = trade.getQty().multiply(unitCostPriceAsBtc);

                trade.setBtcPnl(sellValueAsBtc.subtract(buyValueAsBtc));
                trade.setBtcPnlPercent(sellValueAsBtc.divide(buyValueAsBtc,12, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));

                leg.setRealizedBuyValue(leg.getRealizedBuyValue().add(buyValueAsBtc));
                leg.setRealizedSellValue(leg.getRealizedSellValue().add(sellValueAsBtc));

                /*
                System.out.println("qty : "+qty);
                System.out.println("qty : "+trade.getQty());
                System.out.println("cost : "+cost);
                System.out.println("unitCostPriceAsBtc : "+unitCostPriceAsBtc);
                System.out.println("sellValueAsBtc : "+sellValueAsBtc);
                System.out.println("buyValueAsBtc : "+buyValueAsBtc);
                System.out.println("btcPnl : "+trade.getBtcPnl());
                System.out.println("btcPnlPercent : "+trade.getBtcPnlPercent());
                 */

                qty = qty.subtract(trade.getQty());
            }
            trade.setBalanceLegId(leg.getId());
            tradeService.updateDto(trade);

        }

        leg.setQty(qty);
        leg.setCostAsBtc(cost);

        leg.setTotalPnl(leg.getRealizedSellValue().subtract(leg.getRealizedBuyValue()));

        BigDecimal pnlPercentCumulative = leg.getRealizedSellValue().divide(leg.getRealizedBuyValue(),12, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        BigDecimal pnlPercent = pnlPercentCumulative.subtract(new BigDecimal(100));
        leg.setTotalPnlPercent(pnlPercent);

        if(qty.intValue()==0){
            leg.setIsOpen(false);
            leg.setEndTime(endTime);
        }

        balanceLegRepository.save(leg);
    }

}
