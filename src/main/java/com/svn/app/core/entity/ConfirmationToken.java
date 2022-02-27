package com.svn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class ConfirmationToken {

    @Id
    @SequenceGenerator(name = "seq_confirmation_token", sequenceName = "seq_confirmation_token", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "seq_confirmation_token", strategy = GenerationType.SEQUENCE)
    @Column(name="token_id")
    private long tokenId;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ConfirmationToken(){}

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

}
