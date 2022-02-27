package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "deposit")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Deposit {
	@Id
	@SequenceGenerator(name = "seq_deposit", sequenceName = "seq_deposit", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_deposit", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="wallet_id")
	@JsonIgnoreProperties("tradeList")
	private Wallet wallet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="balance_leg_id")
	@JsonIgnoreProperties("tradeList")
	private BalanceLeg balanceLeg;

	private String coin;
	private Long insertTime;
	private BigDecimal amount;
	private String address;
	private String addressTag;
	private String txId;
	private Integer status;
	private Integer transferType;
	private String confirmTimes;
	private String network;


	private BigDecimal btcValue;
	private BigDecimal btcUsdPrice;
	private BigDecimal userCurrencyValue;
	private BigDecimal commissionBtcValue;
	private BigDecimal btcPnl;
	private BigDecimal btcPnlPercent;

}
