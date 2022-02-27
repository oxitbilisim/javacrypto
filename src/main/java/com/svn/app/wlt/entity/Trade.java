package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "trade")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Trade {
	@Id
	@SequenceGenerator(name = "seq_balance", sequenceName = "seq_balance", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_balance", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="wallet_id")
	@JsonIgnoreProperties("tradeList")
	private Wallet wallet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="balance_leg_id")
	@JsonIgnoreProperties("tradeList")
	private BalanceLeg balanceLeg;

	private Long sId;
	private String symbol;
	private Long orderId;
	private Long orderListId;
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

	@OneToMany(mappedBy = "trade", fetch = FetchType.LAZY)
	private List<TradeConversionPath> tradeConversionPathList;
}
