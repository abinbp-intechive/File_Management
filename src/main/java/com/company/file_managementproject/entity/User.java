package com.company.file_managementproject.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.HasTimeZone;
import io.jmix.core.annotation.Secret;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.security.authentication.JmixUserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@JmixEntity
@Entity
@Table(name = "USER_", indexes = {
        @Index(name = "IDX_USER__ON_USERNAME", columnList = "USERNAME", unique = true),
        @Index(name = "IDX_USER__TEAM", columnList = "TEAM_ID"),
        @Index(name = "IDX_USER__DOMAIN", columnList = "DOMAIN_ID")
})
public class User implements JmixUserDetails, HasTimeZone {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Secret
    @SystemLevel
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @Column(name = "TIME_ZONE_ID")
    private String timeZoneId;

    @JoinColumn(name = "TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "DOMAIN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Domain domain;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities != null ? authorities : Collections.emptyList();
    }

    @Override
    public void setAuthorities(final Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(active);
    }

    @InstanceName
    @DependsOnProperties({"firstName", "lastName", "username"})
    public String getDisplayName() {
        return String.format("%s %s [%s]", (firstName != null ? firstName : ""),
                (lastName != null ? lastName : ""), username).trim();
    }

    @Override
    public String getTimeZoneId() {
        return timeZoneId;
    }

    @Override
    public boolean isAutoTimeZone() {
        return true;
    }

    public void setTimeZoneId(final String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}