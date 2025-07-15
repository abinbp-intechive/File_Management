package com.company.file_managementproject.security;

import com.company.file_managementproject.entity.Domain;
import com.company.file_managementproject.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;

@RowLevelRole(name = "AdminDomainFileRestrict", code = AdminDomainFileRestrictRole.CODE)
public interface AdminDomainFileRestrictRole {
    String CODE = "admin-domain-file-restrict";

    @PredicateRowLevelPolicy(entityClass = Domain.class, actions = {RowLevelPolicyAction.READ, RowLevelPolicyAction.CREATE, RowLevelPolicyAction.UPDATE, RowLevelPolicyAction.DELETE})
    default RowLevelBiPredicate<Domain, ApplicationContext> domainPredicate() {
        return (domain, applicationContext) -> {
            CurrentAuthentication currentAuthentication=applicationContext.getBean(CurrentAuthentication.class);
            User currentUser =(User) currentAuthentication.getUser();
            return domain.getTeam().getMembers().stream()
                    .anyMatch(user->user.getId().equals(currentUser.getId()));
        };
    }
}