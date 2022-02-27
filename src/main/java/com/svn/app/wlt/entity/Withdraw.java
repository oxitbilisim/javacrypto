package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "withdraw")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Withdraw {
	@Id
	@SequenceGenerator(name = "seq_withdraw", sequenceName = "seq_withdraw", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_withdraw", strategy = GenerationType.SEQUENCE)
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
	private BigDecimal amount;
	private BigDecimal transactionFee;
	private String address;
	private String withdrawId;
	private Long applyTime;
	private Integer status;
	private String network;
	private Integer transferType;
	private String txId;

	private BigDecimal btcValue;
	private BigDecimal btcUsdPrice;
	private BigDecimal userCurrencyValue;
	private BigDecimal commissionBtcValue;
	private BigDecimal btcPnl;
	private BigDecimal btcPnlPercent;


}
