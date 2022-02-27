package com.svn.app.wlt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "s_parameter")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Parameter {
	@Id
	@SequenceGenerator(name = "seq_s_parameter", sequenceName = "seq_s_parameter", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_s_parameter", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String category;
	private String code;

	@Column(name = "custom_code_1")
	private String customCode1;

	@Column(name = "custom_code_2")
	private String customCode2;
	private String value;
	private Integer rowNum;
}
