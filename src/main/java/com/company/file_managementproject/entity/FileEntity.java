package com.company.file_managementproject.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "FILE_ENTITY", indexes = {
        @Index(name = "IDX_FILE_ENTITY_TEAM", columnList = "TEAM_ID"),
        @Index(name = "IDX_FILE_ENTITY_DOMAIN", columnList = "DOMAIN_ID"),
        @Index(name = "IDX_FILE_ENTITY_UPLOADED_BY", columnList = "UPLOADED_BY_ID")
})
@Entity
public class FileEntity {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "FILE_NAME")
    private String file_name;

    @Column(name = "FILE_DATA")
    private byte[] file_data;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "UPLOADED_BY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User uploaded_by;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "DOMAIN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Domain domain;

    public void setFile_data(byte[] file_data) {
        this.file_data = file_data;
    }

    public byte[] getFile_data() {
        return file_data;
    }

    public User getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(User uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}