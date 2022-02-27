package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.svn.app.wlt.utility.Types;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "wallet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Wallet {
	@Id
	@SequenceGenerator(name = "seq_wallet", sequenceName = "seq_wallet", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_wallet", strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;
	private String description;
	private String apiKey;
	private String secretKey;
	private Date walletUpdateDate;
	private Date syncDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="exchange_id")
	private Exchange exchange;

	@Enumerated(EnumType.STRING)
	private Types.WalletStatus status;

	@OneToMany(mappedBy = "wallet",fetch = FetchType.LAZY)
	private List<Balance> balanceList;

	@OneToMany(mappedBy = "wallet",fetch = FetchType.LAZY)
	private List<Trade> tradeList;
}
