package com.svn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Table(name = "s_user")
@Entity
@Data
public class User {

    @Id
    @SequenceGenerator(name = "seq_s_user", sequenceName = "seq_s_user", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "seq_s_user", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private boolean isEnabled;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private UserProfile userProfile;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name="s_user_role",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private Set<Role> roles;

}
