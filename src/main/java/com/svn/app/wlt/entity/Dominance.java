package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dominance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Dominance {
	@Id
	private String symbol;
	private BigDecimal dominance;
}
