package com.company.file_managementproject.view.fileentity;

import com.company.file_managementproject.entity.FileEntity;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.view.*;


@Route(value = "file-entities", layout = MainView.class)
@ViewController(id = "FileEntity.list")
@ViewDescriptor(path = "file-entity-list-view.xml")
@LookupComponent("fileEntitiesDataGrid")
@DialogMode(width = "64em")
public class FileEntityListView extends StandardListView<FileEntity> {
    @Subscribe("fileEntity")
    public void onFileEntityFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField> event) {

    }
}