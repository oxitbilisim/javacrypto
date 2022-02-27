package com.svn.app.wlt.entity.dto;

import com.svn.app.wlt.entity.Exchange;
import com.svn.app.wlt.entity.ExchangeInfo;
import com.svn.app.wlt.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeDto {
	private Long id;
	private Long walletId;
	private Long balanceLegId;
	private Long sId;
	private String symbol;
	private Long orderId;
	private Long orderListId;
	private String baseAsset;
	private String quoteAsset;
	private BigDecimal price;
	private BigDecimal qty;
	private BigDecimal quoteQty;
	private BigDecimal commission;
	private String commissionAsset;
	private Long time;
	private Boolean isBuyer;
	private Boolean isMaker;
	private Boolean isBestMatch;
	private BigDecimal btcValue;
	private BigDecimal btcUsdPrice;
	private BigDecimal userCurrencyValue;
	private BigDecimal commissionBtcValue;
	private BigDecimal btcPnl;
	private BigDecimal btcPnlPercent;

	public TradeDto(Trade trade, ExchangeInfo exchangeInfo){
		this.id = trade.getId();
		this.walletId = trade.getWallet().getId();
		this.symbol = trade.getSymbol();
		this.sId = trade.getSId();
		this.orderId = trade.getOrderId();
		this.orderListId = trade.getOrderListId();
		this.price = trade.getPrice();
		this.qty = trade.getQty();
		this.quoteQty = trade.getQuoteQty();
		this.commission = trade.getCommission();
		this.commissionAsset = trade.getCommissionAsset();
		this.time = trade.getTime();
		this.isBuyer = trade.getIsBuyer();
		this.isMaker = trade.getIsMaker();
		this.isBestMatch = trade.getIsBestMatch();
		this.baseAsset=exchangeInfo.getBaseAsset();
		this.quoteAsset=exchangeInfo.getQuoteAsset();

		this.btcValue=trade.getBtcValue();
		this.btcUsdPrice=trade.getBtcUsdPrice();
		this.userCurrencyValue=trade.getUserCurrencyValue();
		this.commissionBtcValue=trade.getCommissionBtcValue();
		this.btcPnl=trade.getBtcPnl();
		this.btcPnlPercent=trade.getBtcPnlPercent();
		this.balanceLegId=trade.getBalanceLeg()!=null?trade.getBalanceLeg().getId():null;

	}
}
