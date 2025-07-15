package com.company.file_managementproject.view.domain;

import com.company.file_managementproject.entity.Domain;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "domains/:id", layout = MainView.class)
@ViewController(id = "Domain_.detail")
@ViewDescriptor(path = "domain-detail-view.xml")
@EditedEntityContainer("domainDc")
public class DomainDetailView extends StandardDetailView<Domain> {
}