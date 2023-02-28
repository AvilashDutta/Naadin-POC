package com.example.application.listener;

import com.example.application.service.AuthService;
import com.example.application.views.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    private static final String AUTH_TOKEN_ATTRIBUTE = "authToken";
    @Autowired
    private AuthService authService;
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget())
                && !checkAuthorization()) {
            event.forwardTo(LoginView.class);
        }
    }

    private boolean checkAuthorization() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session == null) return false;

        String authToken = (String) VaadinSession.getCurrent().getAttribute(AUTH_TOKEN_ATTRIBUTE);
        return authToken!=null && authService.isAuthenticated(authToken);
    }

}
