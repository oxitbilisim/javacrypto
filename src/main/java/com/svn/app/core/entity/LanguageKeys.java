package com.svn.app.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "s_language_keys")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageKeys implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "s_language_keys_id_seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "hibernate_sequence", value = "s_language_keys_id_seq"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "s_language_keys_id_seq")}
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_language_keys_id_seq")
    private Integer id;

    @Column(name = "language_code", nullable = false)
    private String languageCode;

    @Column(name = "key_code", nullable = false)
    private String keyCode;

    @Column(name = "parent_code")
    private Integer parentCode;

    @Column(name = "default_text")
    private String defaultText;

}

