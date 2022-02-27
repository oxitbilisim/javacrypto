package com.svn.app.wlt.entity.dto;

import com.svn.app.wlt.entity.ExchangeInfo;
import com.svn.app.wlt.entity.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawDto {
    private Long id;
    private Long walletId;
    private Long balanceLegId;
    private String baseAsset;
    private String quoteAsset;
    private BigDecimal qty;
    private BigDecimal quoteQty;
    private BigDecimal commission; // transaction fee
    private String commissionAsset; // fee assets
    private Long time;
    private Boolean isBuyer = false; // always false for withdraw

    private BigDecimal btcValue;
    private BigDecimal btcUsdPrice;
    private BigDecimal userCurrencyValue;
    private BigDecimal commissionBtcValue;
    private BigDecimal btcPnl;
    private BigDecimal btcPnlPercent;

    public WithdrawDto(Withdraw withdraw) {
        this.id = withdraw.getId();
        this.walletId = withdraw.getWallet().getId();
        this.balanceLegId=withdraw.getBalanceLeg()!=null?withdraw.getBalanceLeg().getId():null;
        this.baseAsset=withdraw.getCoin();
        this.qty = withdraw.getAmount();
        this.commission = withdraw.getTransactionFee();
        this.commissionAsset = withdraw.getCoin(); // fee assets should be same with base asset
        this.time = withdraw.getApplyTime();

        this.btcValue=withdraw.getBtcValue();
        this.btcUsdPrice=withdraw.getBtcUsdPrice();
        this.userCurrencyValue=withdraw.getUserCurrencyValue();
        this.commissionBtcValue=withdraw.getCommissionBtcValue();
        this.btcPnl=withdraw.getBtcPnl();
        this.btcPnlPercent=withdraw.getBtcPnlPercent();
    }
}
