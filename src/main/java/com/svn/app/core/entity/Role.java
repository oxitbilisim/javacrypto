package com.svn.app.core.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "s_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Role {
	@Id
	@SequenceGenerator(name = "seq_s_role", sequenceName = "seq_s_role", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "seq_s_role", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "role_name")
	private String roleName;
}
