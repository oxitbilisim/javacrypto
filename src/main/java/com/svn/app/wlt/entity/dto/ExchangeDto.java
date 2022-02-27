package com.svn.app.wlt.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto {
	private Long id;
	private String code;
	private String name;
	private String description;
	private Integer rowNum;
	private byte[] logo;
	private Boolean active;
}
