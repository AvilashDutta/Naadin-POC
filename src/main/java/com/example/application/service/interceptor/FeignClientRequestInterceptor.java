package com.example.application.service.interceptor;

import com.vaadin.flow.server.VaadinSession;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignClientRequestInterceptor implements RequestInterceptor {

    @Value("${http.header.auth}")
    private String authHeader;

    private static final String AUTH_TOKEN_ATTRIBUTE = "authToken";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(authHeader, getAuthToken());
    }

    private String getAuthToken() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session == null)  return StringUtils.EMPTY;

        return VaadinSession.getCurrent().getAttribute(AUTH_TOKEN_ATTRIBUTE) != null
                ? "Bearer ".concat((String) VaadinSession.getCurrent().getAttribute(AUTH_TOKEN_ATTRIBUTE)) : StringUtils.EMPTY;
    }

}
