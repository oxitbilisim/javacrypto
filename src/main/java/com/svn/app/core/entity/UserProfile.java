package com.svn.app.core.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Locale;

@Table(name = "s_user_profile")
@Entity
@Data
public class UserProfile {

    @Id
    @SequenceGenerator(name = "seq_s_user_profile", sequenceName = "seq_s_user_profile", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "seq_s_user_profile", strategy = GenerationType.SEQUENCE)
    private long id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_user_id", nullable = false)
    private User user;

    private String companyName;
    private String userLocale;
    private String countryCode;
    private String phone;

    public Locale getLocale(){
        return null;
    }


}
