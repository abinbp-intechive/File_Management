package com.company.file_managementproject.view.fileentity;
import com.company.file_managementproject.entity.FileEntity;
import com.company.file_managementproject.entity.User;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


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
    @ViewComponent
    private DataGrid<FileEntity> fileEntitiesDataGrid;
    @Autowired
    private Dialogs dialogs;
    public FileEntityListView(@Autowired DataManager dataManager,
                              @Autowired CurrentAuthentication currentAuthentication,
                              @Autowired Notifications notifications,
                              @Autowired Dialogs dialog) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
        this.notifications=notifications;
    }
    private void saveFileIntoDb(FileEntity fileEntity,User currentUser){
        fileEntity.setUploaded_by(currentUser);
        fileEntity.setTeam(currentUser.getTeam());
        fileEntity.setDomain(currentUser.getDomain());
        dataManager.save(fileEntity);
        fileEntitiesDc.getMutableItems().add(fileEntity);
    }
    @Subscribe("fileUploadField")
    public void onFileUploadFieldFileUploadSucceeded(final FileUploadSucceededEvent<FileUploadField> event) {
        FileEntity fileEntity = dataManager.create(FileEntity.class);
        fileEntity.setFile_name(event.getFileName());
        fileEntity.setFile_data(event.getSource().getValue());
        User currentUser = (User) currentAuthentication.getUser();
        if(fileEntitiesDc.getItems().stream().anyMatch(existingFile ->existingFile.getFile_name().equals(fileEntity.getFile_name()))) {
//            dialogs.createOptionDialog().withHeader("File named %s already exists")
//                    .withText("Do you want to replace it ?").withActions()
//                    .open();
            notifications.create("File named %s already exists ".formatted(fileEntity.getFile_name())).show();
        }
        else {
            notifications.create("Your file %s has been uploaded successfully.".formatted(event.getFileName()))
                    .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                    .show();
            saveFileIntoDb(fileEntity,currentUser);
        }
    }
    //Dialog Box For Displaying file
    private void htmlFileView(String htmlview){
        Dialog dialog = new Dialog();
        dialog.setWidth("60%");
        dialog.setHeight("90%");
        dialog.add(new Html(htmlview));
        dialog.open();
    }
    @Subscribe
    public void onInit(final InitEvent event) {
        // View Button Code
        fileEntitiesDataGrid.addComponentColumn(file->{
            Button viewBtn = new Button("View");
            viewBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE,ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            viewBtn.getElement().setProperty("title", "View this file");
            viewBtn.addClickListener(click->{
                String htmlview=null;
                if(file.getFile_data()!=null){
                    StreamResource resource=new StreamResource(file.getFile_name(),
                            ()->new ByteArrayInputStream(file.getFile_data()));


                    if (file.getFile_name().endsWith(".pdf")) {
                        resource.setContentType("application/pdf");
                        htmlview = "<iframe src='/files/" + file.getId() + "' " +
                                "width='100%' height='600px' frameborder='0'></iframe>";
                        htmlFileView(htmlview);
                    } else if (file.getFile_name().endsWith(".png") || file.getFile_name().endsWith(".jpg")) {
                        resource.setContentType("image/png");
                        htmlview = "<image src='/files/" + file.getId() + "' " +
                                "width='100%' height='600px' frameborder='0'></image>";
                        htmlFileView(htmlview);
                    }else if(file.getFile_name().endsWith(".txt")) {
                        resource.setContentType("text/plain");
                        String dataurl="data:text/plain;base64,"+Base64.getEncoder().encodeToString(file.getFile_data());
                        htmlview = "<htmlObject data='"+  dataurl + "' " +
                                "width='100%' height='600px' frameborder='0'></htmlObject>";
                        htmlFileView(htmlview);
                    }
                    else if(file.getFile_name().endsWith(".csv")) {
                        resource.setContentType("text/csv");
                        htmlview = "<htmlObject data="+ Base64.getEncoder().encodeToString(file.getFile_data())+ "' " +
                                "width='100%' height='600px' frameborder='0'></htmlObject>";
                        htmlFileView(htmlview);
                    }
                    else {
                        notifications.create("File Unable to preview.").show();
                    }

            } else {
                    notifications.create("File not found.").show();
                }
            });
            return viewBtn;
        });

        // Delete Button Code
        fileEntitiesDataGrid.addComponentColumn(file -> {
            Button deleteBtn = new Button("Delete");
            deleteBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_ERROR);
            deleteBtn.getElement().setProperty("title", "Delete this file");
            deleteBtn.addClickListener(click -> {
                dialogs.createOptionDialog()
                        .withHeader("Confirm Delete")
                        .withText("Delete file: " + file.getFile_name() + "?")
                        .withActions(
                                new DialogAction(DialogAction.Type.YES).withHandler(d -> {
                                    dataManager.remove(file);
                                    fileEntitiesDc.getMutableItems().remove(file);
                                    notifications.create("File deleted").show();
                                }),
                                new DialogAction(DialogAction.Type.NO)
                        )
                        .open();
            });
            return deleteBtn;
        });
    }
    @ViewComponent
    private JmixButton downloadBtn;
    @Subscribe(id = "downloadBtn", subject = "clickListener")
    public void onDownloadBtnClick(final ClickEvent<JmixButton> event) {


        FileEntity selectedFile= fileEntitiesDataGrid.getSingleSelectedItem();
        if (selectedFile == null || selectedFile.getFile_data() == null) {
            notifications.create("Please select a file to download.").show();
            return;
        }
        byte[] fileData=selectedFile.getFile_data();
        String fileName = selectedFile.getFile_name() != null ? selectedFile.getFile_name() : "download.bin";

        StreamResource resource=new StreamResource(fileName,()->new ByteArrayInputStream(fileData));
        resource.setContentType("application/octet-stream");
        notifications.create("Downloading file %s".formatted(fileName)).show();
        String resourceUrl = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource).getResourceUri().toString();
        UI.getCurrent().getPage().executeJs(
                "var link = document.createElement('a');" +
                        "link.href = $0;" +
                        "link.download = $1;" +
                        "document.body.appendChild(link);" +
                        "link.click();" +
                        "document.body.removeChild(link);",
                resourceUrl, fileName);
    }
}