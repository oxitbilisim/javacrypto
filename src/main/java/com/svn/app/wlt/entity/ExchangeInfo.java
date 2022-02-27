package com.svn.app.wlt.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "exchange_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class ExchangeInfo {
	@Id
	private String symbol;
	private String status;
	private String baseAsset;
	private Integer baseAssetPrecision;
	private String quoteAsset;
	private Integer quotePrecision;
	private Integer quoteAssetPrecision;
	private Integer baseCommissionPrecision;
	private Integer quoteCommissionPrecision;
	//private String permissions;

}
