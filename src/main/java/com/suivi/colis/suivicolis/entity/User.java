/*
 * **
 *  * @project : SuiviColis
 *  * @created : 26/04/2024, 01:53
 *  * @modified : 26/04/2024, 00:58
 *  * @description : This file is part of the SuiviColis project.
 *  * @license : MIT License
 * **
 */


package com.suivi.colis.suivicolis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.suivi.colis.suivicolis.model.enums.Role;
import com.suivi.colis.suivicolis.model.enums.UserStatus;
import com.suivi.colis.suivicolis.util.helpers.DateUtils;
import com.suivi.colis.suivicolis.validation.user.UserValidate;
import com.suivi.colis.suivicolis.validation.user.email.ValidEmail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@DiscriminatorColumn(name = Role.USER_ROLE_NAME, discriminatorType = DiscriminatorType.STRING)
@UserValidate
public class User {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ValidEmail
    private String email;

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = Role.USER_ROLE_NAME, insertable = false, updatable = false, nullable = false)
    private String role;

    @Column(nullable = false)
    private String phoneNumber;
    @ManyToOne
    private Address address;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date registeredAt;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date lastUpdateDate;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Column(unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;
    @Column(unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String refreshToken;
    private String cin;

    private Date dateOfBirth;
    private double balance = 0;
    private String image;

    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LoginLog> loginLogs;

    @ManyToOne
    private PrivilegesGroup privilegesGroup ;

    @PrePersist
    protected void onCreated() {
        this.password = passwordEncoder.encode(this.password);
        Date date = new Date();
        this.registeredAt = date;
        this.lastUpdateDate = date;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate = new Date();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}