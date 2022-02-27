package com.svn.app.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "s_language")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language implements Serializable {

    @Id
    @Column(name = "language_code")
    private String id;

    @Column(name = "language" ,nullable = false)
    private String language;

    @Column(name = "rtl")
    private Boolean rtl;

    @Column(name = "row_num")
    private Long rowNum;

    @Column(name = "flag_base64")
    @Lob
    private byte[] flagBase64;

    @Column(name = "active")
    private Boolean active;
}

