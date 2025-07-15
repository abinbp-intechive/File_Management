package com.company.file_managementproject.security;

import com.company.file_managementproject.entity.Domain;
import com.company.file_managementproject.entity.FileEntity;
import com.company.file_managementproject.entity.Team;
import com.company.file_managementproject.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "TeamAdmin", code = TeamAdminRole.CODE)
public interface TeamAdminRole {
    String CODE = "team-admin";

    @MenuPolicy(menuIds = {"User.list", "Domain_.list", "FileEntity.list"})
    @ViewPolicy(viewIds = {"User.list", "Domain_.list", "FileEntity.list", "Team.list", "Domain_.detail", "FileEntity.detail", "Team.detail", "User.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Domain.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Domain.class, actions = EntityPolicyAction.ALL)
    void domain();

    @EntityAttributePolicy(entityClass = FileEntity.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FileEntity.class, actions = EntityPolicyAction.ALL)
    void fileEntity();

    @EntityAttributePolicy(entityClass = Team.class, attributes = {"team_name", "domain", "members", "file"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = Team.class, attributes = "id", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Team.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void team();

    @EntityAttributePolicy(entityClass = User.class, attributes = {"username", "lastName", "firstName", "team"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"id", "email"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();
}