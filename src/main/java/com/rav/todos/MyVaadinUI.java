package com.rav.todos;

import com.rav.bean.Item;
import com.rav.bean.Transaction;
import com.rav.controller.RJSONHandler;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

@Title("CS3Example1")
@Theme("mytheme")
@SuppressWarnings("serial")

public class MyVaadinUI extends UI {

    @WebServlet(urlPatterns = {"/*", "/VAADIN/*"}, asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.rav.todos.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    String userName;
    final File itemFile = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/data/items.json");
    final File transactionFile = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/data/transactions.json");
    final VerticalLayout contentArea = new VerticalLayout();
    
    private void login(final VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addStyleName("login_fl");
        layout.setMargin(true);

        Label label = new Label("<h1 align='center'><b>CS3Bay</b> Example</h1>",ContentMode.HTML);        
        layout.addComponent(label);

        Panel loginPanel = new Panel("Login to CS3Bay Example");
        loginPanel.setSizeFull();
        layout.addComponent(loginPanel);
        FormLayout fl = new FormLayout();
        fl.setSpacing(true);
        fl.addStyleName("login_fl");
        fl.setMargin(true);
        loginPanel.setContent(fl);
        

        final TextField txtUserName = new TextField("User Name");
        fl.addComponent(txtUserName);
        fl.setComponentAlignment(txtUserName, Alignment.MIDDLE_CENTER);

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        fl.addComponent(hl);
        Button btnLogin = new Button("Login", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                if (!txtUserName.getValue().trim().isEmpty()) {
                    getSession().setAttribute("user", txtUserName.getValue());
                    init(request);
                }else{
                    Notification.show("Access Denied", "Invalid User name!", Notification.Type.ERROR_MESSAGE);
                }

            }
        });
        hl.addComponent(btnLogin);
        hl.setComponentAlignment(btnLogin, Alignment.MIDDLE_CENTER);
        
       
      

    }

    @Override
    protected void init(final VaadinRequest request) {
        if (getSession().getAttribute("user") == null) {
        
            login(request);
            return;
        }
        
        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        layout.setMargin(true);
        layout.setSpacing(true);


        MenuBar menuBar = new MenuBar();
        layout.addComponent(menuBar);
        menuBar.setSizeFull();

        
        contentArea.setSizeFull();
        layout.addComponent(contentArea);

        contentArea.addComponent(new ViewItemsPage(getSession()));
        
        MenuBar.MenuItem itemMe = menuBar.addItem("Me", null);
        MenuBar.MenuItem itemLogOut = itemMe.addItem("Log out", null, new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getSession().setAttribute("user", null);
                init(request);
            }

        });

        MenuBar.MenuItem itemItems = menuBar.addItem("Items", null);
        MenuBar.MenuItem itemView = itemItems.addItem("View Items", null, new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                contentArea.removeAllComponents();
                contentArea.addComponent(new ViewItemsPage(getSession()));
            }
        });

        MenuBar.MenuItem itemCart = itemItems.addItem("View Cart", null, new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                contentArea.removeAllComponents();
                contentArea.addComponent(new CartPage(getSession()));
            }
        });

    }

    public class ViewItemsPage extends VerticalLayout {

        private VaadinSession session;

        public ViewItemsPage(final VaadinSession session) {
            this.session = session;
            setSizeFull();
            setSpacing(true);

            addItem(this);
           
        }

    }

    
    final void addItem(VerticalLayout vl) {
        try {
            RJSONHandler<Item> jh = new RJSONHandler<Item>("com.rav.bean.Item", itemFile);
            List<Item> list = jh.readList();
            for (final Item item : list) {
                Panel p = new Panel(item.getName());
                VerticalLayout v = new VerticalLayout();
                v.setMargin(true);
                v.setSpacing(true);
                p.setContent(v);

                Label lblContent;
                if (item.getDescription().length() > 50) {
                    lblContent = new Label(item.getDescription().substring(0, 50) + "...");
                } else {
                    lblContent = new Label(item.getDescription());
                }
                v.addComponent(lblContent);
                HorizontalLayout hl = new HorizontalLayout();
                hl.setSizeFull();
                hl.setSpacing(true);
                Button btnInfo = new Button("Info");
                btnInfo.addListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        InfoModal im = new InfoModal(item.getName(), item.getDescription(), item.getPicture());
                        getCurrent().addWindow(im);
                      
                    }
                });
                hl.addComponent(btnInfo);
                hl.setComponentAlignment(btnInfo, Alignment.MIDDLE_LEFT);

                HorizontalLayout hl2 = new HorizontalLayout();
                Label lblQ = new Label("Quantity : ");
                hl2.addComponent(lblQ);
                final TextField txtQty = new TextField();
                txtQty.setValue("1");
                hl2.addComponent(txtQty);
      

                Button btnAddToCart = new Button("Add To Cart");
                btnAddToCart.addListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Transaction tnx = new Transaction(new Random().nextInt(1000) + 1, getSession().getAttribute("user") + "", item.getName(), new Integer(txtQty.getValue()), new Integer(txtQty.getValue()) * item.getPrice());
                        List<Transaction> cart = (List<Transaction>) getSession().getAttribute("cart");
                        if (cart == null) {
                            cart = new ArrayList<Transaction>();
                        }
                        if (cart.contains(tnx)) {
                            Transaction otnx = cart.get(cart.indexOf(tnx));
                            otnx.setQuantity(otnx.getQuantity() + tnx.getQuantity());
                            otnx.setPrice(otnx.getPrice() + tnx.getPrice());
                        }else{
                            cart.add(tnx);
                        }
                        getSession().setAttribute("cart", cart);
                        Notification.show("Info", "Item Added to cart!", Notification.Type.TRAY_NOTIFICATION);
                        
     
                    }
                });

                hl2.addComponent(btnAddToCart);
                //hl.setComponentAlignment(btnAddToCart, Alignment.MIDDLE_RIGHT);

                hl2.setMargin(true);
                hl.addComponent(hl2);
                hl.setComponentAlignment(hl2, Alignment.MIDDLE_RIGHT);

                v.addComponent(hl);
                vl.addComponent(p);
                vl.setComponentAlignment(p, Alignment.MIDDLE_CENTER);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class InfoModal extends Window {

        public InfoModal(String name, String description, String picture) {
            super(name); // Set window caption
            center();
            setWidth("800px");
            setHeight("600px");
            // Some basic content for the window
            VerticalLayout content = new VerticalLayout();

            String basepath = VaadinService.getCurrent()
                    .getBaseDirectory().getAbsolutePath();

// Image as a file resource
            FileResource resource = new FileResource(new File(basepath
                    + "/WEB-INF/images/" + picture + ".JPG"));

// Show the image in the application
            Image image = new Image("Image from file", resource);
            content.addComponent(image);
            content.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
            content.addComponent(new Label(description));
            content.setMargin(true);
            setContent(content);

            // Disable the close button
            setClosable(false);

            // Trivial logic for closing the sub-window
            Button ok = new Button("OK");
            ok.addClickListener(new ClickListener() {
                public void buttonClick(ClickEvent event) {
                    close(); // Close the sub-window
                }
            });
            content.addComponent(ok);
            content.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
        }
    }

    class CartPage extends VerticalLayout {

        public CartPage(final VaadinSession session) {
            final List<Transaction> cart = (List<Transaction>) session.getAttribute("cart");
            Label label = new Label("My Cart" );
            addComponent(label);
            HorizontalLayout hl = new HorizontalLayout();
            hl.setSizeFull();
            Button btn = new Button("Check out");
            btn.addClickListener(new ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    if (cart != null) {
                        try {                            
                            new RJSONHandler<Transaction>("com.rav.bean.Transaction",transactionFile).writeToList(cart);
                            Notification.show("Info", "Cart Checked Out!", Notification.Type. TRAY_NOTIFICATION);
                            session.setAttribute("cart", null);
                            contentArea.removeAllComponents();
                            contentArea.addComponent(new CartPage(session));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            hl.addComponent(btn);
            hl.setComponentAlignment(btn, Alignment.MIDDLE_RIGHT);
            addComponent(hl);
            Table table = new Table();
            table.addContainerProperty("Name", String.class, null);
            table.addContainerProperty("Qunatity", Integer.class, null);
            table.addContainerProperty("Price", Float.class, null);
            table.setSizeFull();

            addComponent(table);

            if (cart != null) {

                for (Transaction transaction : cart) {
                    table.addItem(new Object[]{transaction.getItem(), transaction.getQuantity(),transaction.getPrice()}, transaction.getId());
                }
            }
        }
    }
}
