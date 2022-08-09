package com.todo.service.authservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.todo.Interface.AuthServiceInterface;
import com.todo.Interface.NeedLogin;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LogManager.getLogger(getClass());

    private static final String AUTH_HEADER_PARAMETER_AUTHERIZATION = "authorization";

    // Get application user name from props.
    @Value("${app.api.basic.username}")
    private String userName;

    // Get application password from props.
    @Value("${app.api.basic.password}")
    private String password;

    @Autowired
    private AuthServiceInterface authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Boolean isValidBasicAuthRequest = false;
        if (handler instanceof HandlerMethod) {
            NeedLogin needLogin = ((HandlerMethod) handler).getMethodAnnotation(NeedLogin.class);
            if (null == needLogin) {
                needLogin = ((HandlerMethod) handler).getMethod().getDeclaringClass()
                        .getAnnotation(NeedLogin.class);
                isValidBasicAuthRequest = true;
            }
            if (null != needLogin) {

                log.info("[Inside PRE Handle interceptor][" + request + "]" + "[" + request.getMethod() + "]"
                        + request.getRequestURI());

                try {

                    // Grab basic header value from request header object.
                    String basicAuthHeaderValue = request.getHeader(AUTH_HEADER_PARAMETER_AUTHERIZATION);

                    // Process basic authentication
                    isValidBasicAuthRequest = authService.validateBasicAuthentication(basicAuthHeaderValue);

                    // If this is invalid request, then set the status as UNAUTHORIZED.
                    if (!isValidBasicAuthRequest) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }

                } catch (Exception e) {
                    log.error("Error occured while authenticating request : " + e.getMessage());
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            }
        }
        return isValidBasicAuthRequest;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }
}
