package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trade_conversion_path")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class TradeConversionPath {
	@Id
	@SequenceGenerator(name = "seq_trade_conversion_path", sequenceName = "seq_trade_conversion_path", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_trade_conversion_path", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="trade_id")
	@JsonIgnoreProperties("tradeConversionPathList")
	private Trade trade;

	private String groupSymbol;
	private String symbol;
	private BigDecimal price;
	private Integer rowNum;
	private Boolean reverseDirection;
	private Long klineTime;

}
