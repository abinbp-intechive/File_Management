package com.company.file_managementproject.security;

import com.company.file_managementproject.entity.Team;
import com.company.file_managementproject.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;

@RowLevelRole(name = "AdminTeamFileRestrict", code = AdminTeamFileRestrictRole.CODE)
public interface AdminTeamFileRestrictRole {
    String CODE = "admin-team-file-restrict";

    @PredicateRowLevelPolicy(entityClass = Team.class, actions = {RowLevelPolicyAction.READ, RowLevelPolicyAction.CREATE, RowLevelPolicyAction.UPDATE})
    default RowLevelBiPredicate<Team, ApplicationContext> teamPredicate() {
        return (team, applicationContext) -> {
            CurrentAuthentication currentAuthentication=applicationContext.getBean(CurrentAuthentication.class);
            User currentUser =(User) currentAuthentication.getUser();
            return team.getMembers().stream().
                    anyMatch(user->user.getId().equals(currentUser.getId()));
        };
    }
}