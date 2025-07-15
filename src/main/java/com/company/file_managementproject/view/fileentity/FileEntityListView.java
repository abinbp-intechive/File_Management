package com.company.file_managementproject.view.fileentity;
import com.company.file_managementproject.entity.FileEntity;
import com.company.file_managementproject.entity.User;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "file-entities", layout = MainView.class)
@ViewController(id = "FileEntity.list")
@ViewDescriptor(path = "file-entity-list-view.xml")
@LookupComponent("fileEntitiesDataGrid")
@DialogMode(width = "64em")
public class FileEntityListView extends StandardListView<FileEntity> {
    private DataManager dataManager;
    private CurrentAuthentication currentAuthentication;
    private Notifications notifications;
    @ViewComponent
    private CollectionContainer<FileEntity> fileEntitiesDc;
    public FileEntityListView(@Autowired DataManager dataManager,@Autowired CurrentAuthentication currentAuthentication,@Autowired Notifications notifications) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
        this.notifications=notifications;
    }
    @Subscribe("fileUploadField")
    public void onFileUploadFieldFileUploadSucceeded(final FileUploadSucceededEvent<FileUploadField> event) {
        notifications.create("Your file %s has been uploaded successfully.".formatted(event.getFileName()))
                .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                .show();
        FileEntity fileEntity = dataManager.create(FileEntity.class);
        fileEntity.setFile_name(event.getFileName());
        fileEntity.setFile_data(event.getSource().getValue());
        User currentUser = (User) currentAuthentication.getUser();
        fileEntity.setUploaded_by(currentUser);
        fileEntity.setTeam(currentUser.getTeam());
        fileEntity.setDomain(currentUser.getDomain());
        dataManager.save(fileEntity);
        fileEntitiesDc.getMutableItems().add(fileEntity);
    }
}