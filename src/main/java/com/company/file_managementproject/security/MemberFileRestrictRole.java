package com.company.file_managementproject.security;

import com.company.file_managementproject.entity.FileEntity;
import com.company.file_managementproject.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;

@RowLevelRole(name = "MemberFileRestrict", code = MemberFileRestrictRole.CODE)
public interface MemberFileRestrictRole {
    String CODE = "member-file-restrict";

    @PredicateRowLevelPolicy(entityClass = FileEntity.class, actions = {RowLevelPolicyAction.READ, RowLevelPolicyAction.UPDATE, RowLevelPolicyAction.DELETE})
    default RowLevelBiPredicate<FileEntity, ApplicationContext> fileEntityPredicate() {
        return (fileEntity, applicationContext) -> {
            CurrentAuthentication currentAuthentication= applicationContext.getBean(CurrentAuthentication.class);
            User currentUser=(User) currentAuthentication.getUser();
            return fileEntity.getTeam().getMembers().stream().anyMatch(user->user.getId().
                    equals(currentUser.getId()));
        };
    }
}