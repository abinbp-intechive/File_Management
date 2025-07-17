package com.company.file_managementproject.view.user;

import com.company.file_managementproject.entity.User;
import com.company.file_managementproject.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "assign_users", layout = MainView.class)
@ViewController(id = "AssignUser.list")
@ViewDescriptor(path = "Assignuser-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class AssignUserListView extends StandardListView<User> {
    @ViewComponent
    private JmixButton addTeam;
    @ViewComponent
    private DataGrid<User> usersDataGrid;
    @Autowired
    private ViewNavigators viewNavigators;

    public AssignUserListView(@Autowired ViewNavigators viewNavigators) {
        this.addTeam = addTeam;
    }

    @Subscribe(id = "addTeam", subject = "clickListener")
    public void onAddTeamClick(final ClickEvent<JmixButton> event) {
        User selectedUser=usersDataGrid.getSingleSelectedItem();
        if(selectedUser!=null){
        //    viewNavigators.detailView(User.class).editEntity(selectedUser).withViewId("assignuser-detail-view").open;
        }
    }
}