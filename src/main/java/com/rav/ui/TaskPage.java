/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rav.ui;



import com.rav.todos.MyVaadinUI;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ravklk
 *//*
public class TaskPage extends TabSheet {

    private VerticalLayout vl;
    private VerticalLayout vl2;
    private VerticalLayout vl3;
    TaskMember taskMemeber;
    VaadinSession session;

    public TaskPage(TaskMember taskMember, VaadinSession session) {
        try {
            this.taskMemeber = taskMember;
            this.session = session;
            setSizeFull();
       // setMargin(true);
            //setSpacing(true);

            // TabSheet ts = new TabSheet();
            Panel inCompleteTasksPanel = new Panel();
            vl = new VerticalLayout();
            vl2 = new VerticalLayout();
            vl.setSizeFull();
            vl.setSpacing(true);
            vl.setMargin(true);

            inCompleteTasksPanel.setContent(vl);
            addTab(inCompleteTasksPanel, "Task to Complete");

            Panel completeTasksPanel = new Panel();

            completeTasksPanel.setContent(vl2);
            vl2.setSizeFull();
            vl2.setSpacing(true);
            vl2.setMargin(true);
            addTab(completeTasksPanel, "Tasks Completed");

            Panel summaryTasksPanel = new Panel();
            vl3 = new VerticalLayout();
            vl3.setSizeFull();
            vl3.setSpacing(true);
            vl3.setMargin(true);

            summaryTasksPanel.setContent(vl3);
            addTab(summaryTasksPanel, "Task Summary");


            /*
             List<Task> incompleteTasks = new TaskJpaController().findTasksByState(Task.NOT_COMPLETED);
             for (final Task task : incompleteTasks) {
             addInCompleteTask(task);

             }

             List<Task> completeTasks = ((TaskMember)getSession().getAttribute("user")).getTasks();//new TaskJpaController().findTasksByState(Task.COMPLETED);

             for (Task task : completeTasks) {
             addCompletedTask(task);
             }
             
        //System.out.println("####################### > "  + getSession());
            //TaskMember taskmember = (TaskMember)getSession().getAttribute("user");
            //System.out.println("2222222222222222222222task member > " + taskmember);
            List<Task> tasks = taskMember.getTasks();
            System.out.println("lines > " + tasks.size());
            for (Task task : tasks) {
                System.out.println("task :> " + task);
                System.out.println(" stat :>> " + task.getStatus());
                if (task.getStatus() == Task.COMPLETED) {
                    addCompletedTask(task);
                } else {
                    addInCompleteTask(task);
                }
            }
            
            HorizontalLayout hlSummaryRange = new HorizontalLayout();
            hlSummaryRange.addComponent(new Label("Summary From "));
            Date today = new Date();
            
            DateField dtStart = new DateField("From");
          //  dtStart.setValue(new Date(today.getYear(), today.getMonth()));
            
            Table summaryTable = new Table();
            summaryTable.setSizeFull();
            summaryTable.addContainerProperty("Task Name", String.class, null);
            summaryTable.addContainerProperty("Description", String.class, null);
            summaryTable.addContainerProperty("Start Date", Date.class, null);
            summaryTable.addContainerProperty("End Date", Date.class, null);
            summaryTable.addContainerProperty("Status", String.class, null);
            vl3.addComponent(summaryTable); 
        } catch (Exception e) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> error >>>>>>>>>>>");
            e.printStackTrace();
        }
    }

    final void addInCompleteTask(final Task task) {
        final Panel p = new Panel(task.getName());
        VerticalLayout v = new VerticalLayout();
        v.setMargin(true);
        v.setSpacing(true);
        p.setContent(v);

        Label lblEndDate = new Label("Task to be completed by : " + new SimpleDateFormat("dd/MM/yyyy").format(task.getEndDate()));
        Label lblContent = new Label(task.getDescription());
        v.addComponent(lblEndDate);
        v.addComponent(lblContent);

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.setSpacing(true);
        Button btnEdit = new Button("Edit");

        btnEdit.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
              // MyVaadinUI.EditTaskPage ep = new MyVaadinUI().new EditTaskPage(1, task, taskMemeber, session);
                VerticalLayout parent = (VerticalLayout) getParent();
                parent.removeAllComponents();
             //   parent.addComponent(ep);
            }
        });
        hl.addComponent(btnEdit);
        hl.setComponentAlignment(btnEdit, Alignment.MIDDLE_LEFT);

        Button btnFinish = new Button("Mark As Finished");
        btnFinish.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    new TaskJpaController().markTaskCompleted(task);
                    System.out.println("1");
                    vl.removeComponent(p);
                    System.out.println("2");
                    addCompletedTask(task);
                    System.out.println("3");
                    Notification.show("To Do",
                            "Task marked as completed!",
                            Notification.Type.TRAY_NOTIFICATION);
                } catch (Exception e) {
                    e.printStackTrace();
                    Notification.show("Error marking task completed",
                            e.getMessage(),
                            Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        hl.addComponent(btnFinish);
        hl.setComponentAlignment(btnFinish, Alignment.MIDDLE_RIGHT);

        v.addComponent(hl);
        vl.addComponent(p);
        vl.setComponentAlignment(p, Alignment.MIDDLE_CENTER);
    }

    final void addCompletedTask(final Task task) {
        System.out.println("task is " + task);
        Panel p = new Panel(task.getName());
        VerticalLayout v = new VerticalLayout();
        v.setMargin(true);
        v.setSpacing(true);
        p.setContent(v);

        Label lblEndDate = new Label("Completed On : " + new SimpleDateFormat("dd/MM/yyyy").format(task.getCompletedDate()));
        Label lblContent = new Label(task.getDescription());
        v.addComponent(lblEndDate);
        v.addComponent(lblContent);
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.setSpacing(true);
        Button btnEdit = new Button("Edit");
        btnEdit.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
             //   MyVaadinUI.EditTaskPage ep = new MyVaadinUI().new EditTaskPage(1, task, taskMemeber, session);
                VerticalLayout parent = (VerticalLayout) getParent();
                parent.removeAllComponents();
              //  parent.addComponent(ep);
            }
        });
        hl.addComponent(btnEdit);
        hl.setComponentAlignment(btnEdit, Alignment.MIDDLE_LEFT);

        v.addComponent(hl);
        vl2.addComponent(p);
        vl2.setComponentAlignment(p, Alignment.MIDDLE_CENTER);

    }

}
*/