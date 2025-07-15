package com.company.file_managementproject.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "TEAM")
@Entity
public class Team {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "TEAM_NAME")
    private String team_name;

    @OneToMany(mappedBy = "team")
    private List<Domain> domain;

    @OneToMany(mappedBy = "team")
    private List<User> members;

    @OneToMany(mappedBy = "team")
    private List<FileEntity> file;

    public List<FileEntity> getFile() {
        return file;
    }

    public void setFile(List<FileEntity> file) {
        this.file = file;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Domain> getDomain() {
        return domain;
    }

    public void setDomain(List<Domain> domain) {
        this.domain = domain;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}