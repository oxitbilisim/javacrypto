package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_exchange")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Order {
	@Id
	@SequenceGenerator(name = "seq_order_exchange", sequenceName = "seq_order_exchange", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_order_exchange", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="wallet_id")
	@JsonIgnoreProperties("tradeList")
	private Wallet wallet;

	private String symbol;
	private Long orderId;
	private Long orderListId;
	private String clientOrderId;
	private BigDecimal price;
	private BigDecimal origQty;
	private BigDecimal executedQty;
	private BigDecimal cummulativeQuoteQty;
	private String status;
	private String timeInForce;
	private String type;
	private String side;
	private BigDecimal stopPrice;
	private BigDecimal icebergQty;
	private Long time;
	private Long updateTime;
	private Boolean isWorking;
	private BigDecimal origQuoteOrderQty;
}
