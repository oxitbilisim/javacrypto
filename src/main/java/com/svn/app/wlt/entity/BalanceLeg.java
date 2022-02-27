package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "balance_leg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class BalanceLeg {
	@Id
	@SequenceGenerator(name = "seq_balance", sequenceName = "seq_balance", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_balance", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="balance_id")
	@JsonIgnoreProperties("balanceLegList")
	private Balance balance;

	@OneToMany(mappedBy = "balanceLeg",fetch = FetchType.LAZY)
	private List<Trade> tradeList;

	@OneToMany(mappedBy = "balanceLeg",fetch = FetchType.LAZY)
	private List<Withdraw> withdrawList;

	@OneToMany(mappedBy = "balanceLeg",fetch = FetchType.LAZY)
	private List<Deposit> depositList;

	private BigDecimal totalPnlPercent;
	private BigDecimal totalPnl;
	private BigDecimal qty;
	private BigDecimal costAsBtc;
	private BigDecimal realizedBuyValue;
	private BigDecimal realizedSellValue;
	private Boolean isOpen;
	private Long startTime;
	private Long endTime;

}
