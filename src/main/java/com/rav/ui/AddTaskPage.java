/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rav.ui;



import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author ravklk
 */
public class AddTaskPage extends FormLayout {
/*
    public AddTaskPage() {
        setSizeFull();
        setSpacing(true);

        final TextField txtName = new TextField("Task Name");
        txtName.setRequired(true);
        //txtName.setRequiredError("Task name cannot be empty!");
        txtName.setSizeFull();
        addComponent(txtName);

        final TextArea txtDescription = new TextArea("Description");
        txtDescription.setSizeFull();
        addComponent(txtDescription);

        final DateField dtStart = new DateField("Task starts from");
        dtStart.setSizeFull();
        dtStart.setValue(new Date());
        addComponent(dtStart);

        final DateField dtEnd = new DateField("Should completed by");
        dtEnd.setSizeFull();
        addComponent(dtEnd);

        Button btnAdd = new Button("Add", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Task task = new Task();
                task.setName(txtName.getValue());
                task.setDescription(txtDescription.getValue());
                task.setStartDate(dtStart.getValue());
                task.setEndDate(dtEnd.getValue());
                Date dt = new Date();
                task.setPlacedDate(dt);
                task.setPlacedTime(dt);
                try {
                    EntityManager em = null;
                    try {
                        em = JPAContainerFactory.createEntityManagerForPersistenceUnit("com.rav_Todos_war_1.0-SNAPSHOTPU");
                        Query q = em.createQuery("select MAX(t.id) from Task t");
                        Object o = q.getResultList();
                        Long id = 0L;
                        if (o != null) {
                            id = Long.parseLong(o + "");
                        }
                        id++;
                        System.out.println("id id " + id);
                        task.setId(id);
                        em.getTransaction().begin();
                        em.persist(task);
                        em.getTransaction().commit();
                    } catch (Exception ex) {
                        try {
                            em.getTransaction().rollback();
                        } catch (Exception re) {
                            ex.printStackTrace();
                        }
                        throw ex;
                    } finally {
                        if (em != null) {
                            em.close();
                        }
                    }
                    VerticalLayout parent = (VerticalLayout) getParent();
                    parent.removeAllComponents();
                    Notification.show("To Do",
                            "Task Added Successfully!",
                            Notification.Type.TRAY_NOTIFICATION);
                } catch (Exception e) {
                    e.printStackTrace();
                    Notification.show("Error adding task",
                            e.getMessage(),
                            Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        btnAdd.setSizeFull();
        addComponent(btnAdd);

        Button btnCancel = new Button("Cancel", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                VerticalLayout parent = (VerticalLayout) getParent();
                parent.removeAllComponents();
            }
        });
        btnCancel.setSizeFull();
        addComponent(btnCancel);

    }*/

}
