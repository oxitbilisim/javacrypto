package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "balance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Balance {
	@Id
	@SequenceGenerator(name = "seq_balance", sequenceName = "seq_balance", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_balance", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="wallet_id")
	@JsonIgnoreProperties("balanceList")
	private Wallet wallet;

	@OneToMany(mappedBy = "balance",fetch = FetchType.LAZY)
	private List<BalanceLeg> balanceLegList;

	private String asset;
	private BigDecimal free;
	private BigDecimal locked;

}
