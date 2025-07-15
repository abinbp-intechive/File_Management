package com.company.file_managementproject.view.team;

import com.company.file_managementproject.entity.Team;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "teams", layout = MainView.class)
@ViewController(id = "Team.list")
@ViewDescriptor(path = "team-list-view.xml")
@LookupComponent("teamsDataGrid")
@DialogMode(width = "64em")
public class TeamListView extends StandardListView<Team> {
}