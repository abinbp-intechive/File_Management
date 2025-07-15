package com.company.file_managementproject.view.user;

import com.company.file_managementproject.entity.User;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "assign_users", layout = MainView.class)
@ViewController(id = "AssignUser.list")
@ViewDescriptor(path = "Assignuser-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class AssignUserListView extends StandardListView<User> {
}