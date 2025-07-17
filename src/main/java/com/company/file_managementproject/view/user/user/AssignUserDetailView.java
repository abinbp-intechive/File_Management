package com.company.file_managementproject.view.user.user;

import com.company.file_managementproject.entity.User;

import com.company.file_managementproject.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "Assignusers/:id", layout = MainView.class)
@ViewController(id = "AssignUser.detail")
@ViewDescriptor(path = "Assignuser-detail-view.xml")
@EditedEntityContainer("userDc")
public class AssignUserDetailView extends StandardDetailView<User> {
}