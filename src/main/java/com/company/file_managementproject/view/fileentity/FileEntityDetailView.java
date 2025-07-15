package com.company.file_managementproject.view.fileentity;

import com.company.file_managementproject.entity.FileEntity;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "file-entities/:id", layout = MainView.class)
@ViewController(id = "FileEntity.detail")
@ViewDescriptor(path = "file-entity-detail-view.xml")
@EditedEntityContainer("fileEntityDc")
public class FileEntityDetailView extends StandardDetailView<FileEntity> {
}