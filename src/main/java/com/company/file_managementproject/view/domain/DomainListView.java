package com.company.file_managementproject.view.domain;

import com.company.file_managementproject.entity.Domain;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "domains", layout = MainView.class)
@ViewController(id = "Domain_.list")
@ViewDescriptor(path = "domain-list-view.xml")
@LookupComponent("domainsDataGrid")
@DialogMode(width = "64em")
public class DomainListView extends StandardListView<Domain> {
}