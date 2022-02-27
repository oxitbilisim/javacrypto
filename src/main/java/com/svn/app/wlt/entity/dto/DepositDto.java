package com.svn.app.wlt.entity.dto;

import com.svn.app.wlt.entity.Deposit;
import com.svn.app.wlt.entity.ExchangeInfo;
import com.svn.app.wlt.entity.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositDto {
    private Long id;
    private Long walletId;
    private Long balanceLegId;
    private String baseAsset;
    private String quoteAsset;
    private BigDecimal qty;
    private BigDecimal quoteQty;
    private Long time;
    private Boolean isBuyer = true; // always true for deposit

    private BigDecimal btcValue;
    private BigDecimal btcUsdPrice;
    private BigDecimal userCurrencyValue;
    private BigDecimal commissionBtcValue;
    private BigDecimal btcPnl;
    private BigDecimal btcPnlPercent;

    public DepositDto(Deposit deposit, ExchangeInfo exchangeInfo) {
        this.id = deposit.getId();
        this.walletId = deposit.getWallet().getId();
        this.balanceLegId=deposit.getBalanceLeg()!=null?deposit.getBalanceLeg().getId():null;
        this.baseAsset=exchangeInfo.getBaseAsset();
        this.quoteAsset=exchangeInfo.getQuoteAsset();
        this.qty = deposit.getAmount();
        this.time = deposit.getInsertTime();

        this.btcValue=deposit.getBtcValue();
        this.btcUsdPrice=deposit.getBtcUsdPrice();
        this.userCurrencyValue=deposit.getUserCurrencyValue();
        this.commissionBtcValue=deposit.getCommissionBtcValue();
        this.btcPnl=deposit.getBtcPnl();
        this.btcPnlPercent=deposit.getBtcPnlPercent();
    }
}
