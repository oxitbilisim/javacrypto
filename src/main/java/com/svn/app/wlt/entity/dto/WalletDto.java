package com.svn.app.wlt.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.svn.app.wlt.entity.Balance;
import com.svn.app.wlt.entity.Exchange;
import com.svn.app.wlt.entity.Trade;
import com.svn.app.wlt.utility.Types;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

	private Long id;
	private String name;
	private String description;
	private String apiKey;
	private String secretKey;
	private Date walletUpdateDate;
	private Date syncDate;
	private Long exchangeId;
	private Types.WalletStatus status;
}
