package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "label")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Label {
	@Id
	@SequenceGenerator(name = "seq_label", sequenceName = "seq_label", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_label", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private String description;
	private String colorCode;
}
