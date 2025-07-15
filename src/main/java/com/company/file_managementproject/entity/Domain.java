package com.company.file_managementproject.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "DOMAIN_", indexes = {
        @Index(name = "IDX_DOMAIN__TEAM", columnList = "TEAM_ID")
})
@Entity(name = "Domain_")
public class Domain {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "DOMAIN_NAME")
    private String domain_name;

    @OneToMany(mappedBy = "domain")
    private List<FileEntity> file;

    @JoinColumn(name = "TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public List<FileEntity> getFile() {
        return file;
    }

    public void setFile(List<FileEntity> file) {
        this.file = file;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
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

    public void setId(UUID id) {
        this.id = id;
    }

}