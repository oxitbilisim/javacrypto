package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "exchange")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Exchange {
	@Id
	@SequenceGenerator(name = "seq_exchange", sequenceName = "seq_exchange", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_exchange", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String code;
	private String name;
	private String description;
	private Integer rowNum;
	private byte[] logo;
	private Boolean active;

	@OneToMany(mappedBy = "exchange",fetch = FetchType.LAZY)
	private List<Wallet> walletList;

}
