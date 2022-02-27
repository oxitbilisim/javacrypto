package com.svn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class PasswordResetToken {

    private static final Long EXPIRATION = 60L * 24L*1000L * 60L;

    @Id
    @SequenceGenerator(name = "seq_password_reset_token", sequenceName = "seq_password_reset_token", initialValue = 5, allocationSize = 1)
    @GeneratedValue(generator = "seq_password_reset_token", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Date expiryDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    public PasswordResetToken() {
    }

    public PasswordResetToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.createdDate = new Date();
        this.expiryDate =  new Date(new Date().getTime()+EXPIRATION);
    }
}
