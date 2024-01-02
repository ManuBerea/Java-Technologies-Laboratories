package com.example.laborator7.controller;

import com.example.laborator7.bean.UserBean;
import com.example.laborator7.model.User;
import com.example.laborator7.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
@Named
public class UserController implements Serializable {

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

    public void login() {
        try {
            User user = userService.loginUser(userBean.convertToEntity());
            setCookies(user);
            redirectToPage(user.isAdmin() ? "viewTimetables.xhtml" : "addTimetable.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() throws IOException {
        removeCookies("user", "isAdmin", "userId");
        redirectToPage("login.xhtml");
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
