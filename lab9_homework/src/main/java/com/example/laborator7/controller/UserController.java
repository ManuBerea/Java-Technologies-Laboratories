package com.example.laborator7.controller;

import com.example.laborator7.bean.UserBean;
import com.example.laborator7.filter.CacheSubmissionsFilter;
import com.example.laborator7.model.User;
import com.example.laborator7.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationScoped
@Named
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(CacheSubmissionsFilter.class.getName());

    @Inject
    private UserService userService;

    @Inject
    private UserBean userBean;

    public UserController() {
    }

    public void register() {
        try {
            userService.createUser(userBean.convertToEntity());
            redirectToPage("login.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectBasedOnRole() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        LOGGER.log(Level.INFO, "HERE1: {0}", request.isUserInRole("admin"));

        User user = userService.findUserByUsername(request.getUserPrincipal().getName());
        setCookies(user);

        if (request.isUserInRole("admin")) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/admin/viewTimetables.xhtml");
        } else {
            externalContext.redirect(externalContext.getRequestContextPath() + "/user/addTimetable.xhtml");
        }
    }

    public void redirectIfLoggedIn() throws IOException {
        if (isLoggedIn()) {
            LOGGER.log(Level.INFO, "is logged in");
            redirectBasedOnRole();
        }
        else {
            LOGGER.log(Level.INFO, "is not logged in");
            redirectToPage("login.xhtml");
        }

    }

    public boolean isLoggedIn() {
        LOGGER.log(Level.INFO, "HERE2: {0}", FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal());
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
    }

    public void logout() throws IOException {
        removeCookies("user", "userId", "isAdmin");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        try {
            request.logout();
            redirectToPage("../login.xhtml");
        } catch (ServletException | IOException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            e.printStackTrace();
        }
    }

    private void redirectToPage(String page) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(page);
    }


    private void setCookies(User user) {
        setCookie("user", user.getUsername());
        setCookie("userId", String.valueOf(user.getUserId()));
        setCookie("isAdmin", String.valueOf(user.isAdmin()));
    }

    private void removeCookies(String... cookieNames) {
        for (String cookieName : cookieNames) {
            Cookie cookie = getCookie(cookieName);
            cookie.setValue("");
            setCookieProperties(cookie);
        }
    }

    private Cookie getCookie(String cookieName) {
        return (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(cookieName);
    }

    private void setCookie(String cookieName, String cookieValue) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 31536000);
        properties.put("path", "/");
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(
                cookieName,
                URLEncoder.encode(cookieValue, StandardCharsets.UTF_8),
                properties
        );
    }

    private void setCookieProperties(Cookie cookie) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 0);
        properties.put("path", "/");
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(
                cookie.getName(),
                cookie.getValue(),
                properties
        );
    }

}
