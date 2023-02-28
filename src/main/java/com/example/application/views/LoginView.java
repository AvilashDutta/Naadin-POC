package com.example.application.views;

import com.example.application.service.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    public static final String NAME = "login";
    private TextField usernameField;
    private PasswordField passwordField;

    @Autowired
    private AuthService authService;

    public LoginView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);


        Image logo = new Image("https://fastly.picsum.photos/id/768/200/300.jpg?hmac=lFX2oZVTUayugh_YZQ5q6uoXJFYaOJz3d2_GLaIW2aU", "Logo");
        logo.setWidth("150px");

        usernameField = new TextField("Username");
        usernameField.setWidth("300px");
        usernameField.setRequired(true);
        usernameField.setErrorMessage("Username is required");

        passwordField = new PasswordField("Password");
        passwordField.setWidth("300px");
        passwordField.setRequired(true);
        passwordField.setErrorMessage("Password is required");


        Button loginButton = new Button("Login", event -> {
            if (validateFields()) {
                try {
                    String token = authService.getAuthToken(usernameField.getValue(), passwordField.getValue());
                    VaadinSession.getCurrent().setAttribute("authToken", token);
                    // Redirect the user to the protected page
                    UI.getCurrent().navigate("");
                } catch (Exception e) {
                    Notification.show("Invalid username or password");
                }
            }
        });
        loginButton.setWidth("300px");

        FormLayout formLayout = new FormLayout();
        formLayout.add(logo, usernameField, passwordField, loginButton);
        formLayout.setMaxWidth("400px");
        add(formLayout);

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 1));


    }

    private boolean validateFields() {
        boolean isValid = true;
        if (usernameField.isEmpty()) {
            usernameField.setInvalid(true);
            isValid = false;
        }
        if (passwordField.isEmpty()) {
            passwordField.setInvalid(true);
            isValid = false;
        }
        return isValid;
    }

}
