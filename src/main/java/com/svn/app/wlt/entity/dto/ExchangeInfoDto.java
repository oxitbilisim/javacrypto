package com.svn.app.wlt.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeInfoDto {

	private String symbol;
	private String baseAsset;
	private String quoteAsset;
	private BigDecimal baseAssetDominance;
	private BigDecimal quoteAssetDominance;
	/*private BigDecimal quoteAssetPrice;*/
}
