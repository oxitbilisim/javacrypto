package com.svn.app.wlt.service;

import com.svn.app.wlt.entity.Balance;
import com.svn.app.wlt.entity.BalanceLeg;
import com.svn.app.wlt.entity.dto.TradeDto;
import com.svn.app.wlt.repository.BalanceLegRepository;
import com.svn.app.wlt.repository.BalanceRepository;
import com.svn.app.wlt.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService {
    private BalanceRepository balanceRepository;
    private BalanceLegService balanceLegService;
    private TradeService tradeService;

    public BalanceService(BalanceRepository balanceRepository,
                          TradeService tradeService,
                          BalanceLegService balanceLegService) {
        this.balanceRepository = balanceRepository;
        this.tradeService = tradeService;
        this.balanceLegService = balanceLegService;
    }

    public Balance findById(Long id){
        return balanceRepository.findById(id).get();
    }

    public Balance create(Balance balance){
        balance.setId(null);
        return balanceRepository.save(balance);
    }

    public Balance update(Balance balance){
        return balanceRepository.save(balance);
    }

    public void deleteById(Long id){
        balanceRepository.deleteById(id);
    }

    public List<Balance> saveAll(List<Balance> balanceList){
        return balanceRepository.saveAll(balanceList);
    }

    public void calculatePNL(String asset){
        Balance balance = balanceRepository.findByAsset(asset);
        if(balance==null)
            return;

        List<TradeDto> tradeList = tradeService.findAllByBaseAssetAndLegIsNullAsDto(asset);

        BigDecimal legTotal = new BigDecimal(0);
        List<TradeDto> batch = new ArrayList<>();
        for (TradeDto trade: tradeList) {
            batch.add(trade);
            if(trade.getIsBuyer()) {
                legTotal = legTotal.add(trade.getQty());
            }else{
                legTotal = legTotal.subtract(trade.getQty());
            }

            if(legTotal.intValue()==0){
                balanceLegService.calculateLegPNL(balance,batch);
                batch = new ArrayList<>();
            }

        }
        balanceLegService.calculateLegPNL(balance,batch);

    }
}
