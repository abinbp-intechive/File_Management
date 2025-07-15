package com.company.file_managementproject.view.team;

import com.company.file_managementproject.entity.Team;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "teams/:id", layout = MainView.class)
@ViewController(id = "Team.detail")
@ViewDescriptor(path = "team-detail-view.xml")
@EditedEntityContainer("teamDc")
public class TeamDetailView extends StandardDetailView<Team> {
}