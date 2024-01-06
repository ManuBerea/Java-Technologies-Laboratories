package com.example.laborator7.controller;

import com.example.laborator7.CacheSubmissionsFilter;
import com.example.laborator7.bean.TimetableBean;
import com.example.laborator7.model.Timetable;
import com.example.laborator7.model.User;
import com.example.laborator7.service.TimetableSubmissionService;
import lombok.Getter;
import lombok.Setter;
import com.example.laborator7.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Named
@Getter
@Setter
public class TimetableController implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private TimetableSubmissionService timetableService;

    @Inject
    private Instance<String> registrationNumberInstance;

    @Inject
    private TimetableBean timetableBean;

    private Timetable selectedTimetable;

    private Long selectedTimetableId;

    private static final String TIMETABLES_URI = "http://localhost:50253/Laborator7-1.0-SNAPSHOT/api/timetables";

    private Client client;

    public TimetableController() {
        client = ClientBuilder.newClient().register(CacheSubmissionsFilter.class);
    }

    private User getCurrentUser() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookiesMap = externalContext.getRequestCookieMap();

        if (cookiesMap.containsKey("user")) {
            Cookie userCookie = (Cookie) cookiesMap.get("user");
            String username = userCookie.getValue();
            return userService.findUserByUsername(username);
        }
        return null;
    }

    public void addTimeTable() {
        timetableBean.setUser(getCurrentUser());
        timetableBean.setRegistrationNumber(registrationNumberInstance.get());
        try {
            Timetable timetable = timetableBean.convertToEntity();
            Response response = client.target(TIMETABLES_URI)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(timetable, MediaType.APPLICATION_JSON));
            handleResponse(response, "Timetable successfully added.", "Failed to add timetable: ");
        } catch (Exception e) {
            addErrorMessage(e);
        }
    }

    public void updateTimetable() {
        try {
            if (selectedTimetable != null && selectedTimetableId != null) {
                selectedTimetable.setId(selectedTimetableId);

                System.out.println("Updating Timetable: " + selectedTimetable);

                Response response = client.target(TIMETABLES_URI)
                        .path("/{id}")
                        .resolveTemplate("id", selectedTimetableId)
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(selectedTimetable, MediaType.APPLICATION_JSON));

                handleResponse(response, "Timetable successfully updated.", "Failed to update timetable: ");

            } else {
                addErrorMessage(new Exception("No timetable selected for update."));
            }
        } catch (Exception e) {
            addErrorMessage(e);
            e.printStackTrace();
        }
    }

    public void refreshTimetables() {
        getAllTimetablePreferences();
    }

    public void deleteTimetable(Long id) {
        try {
            Response response = client.target(TIMETABLES_URI)
                    .path("/{id}")
                    .resolveTemplate("id", id)
                    .request()
                    .delete();
            handleResponse(response, "Timetable successfully deleted.", "Failed to delete timetable: ");
        } catch (Exception e) {
            addErrorMessage(e);
        }
    }

    public List<Timetable> getAllTimetablePreferences() {
        try {
            Response response = client.target(TIMETABLES_URI)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(new GenericType<List<Timetable>>(){});
            } else {
                addErrorMessage(new Exception("Failed to retrieve timetables: " + response.getStatus()));
                return new ArrayList<>();
            }
        } catch (Exception e) {
            addErrorMessage(e);
            return new ArrayList<>();
        }
    }

    private void handleResponse(Response response, String successMsg, String errorMsg) {
        if (response.getStatus() == Response.Status.CREATED.getStatusCode() || response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success:", successMsg));
        } else {
            String errorMessage = response.readEntity(String.class);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", errorMsg + errorMessage));
        }
    }

    private void addErrorMessage(Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Exception occurred: " + e.getMessage()));
    }

    public Timetable getSelectedTimetable() {
        return selectedTimetable;
    }

    public void setSelectedTimetable(Timetable selectedTimetable) {
        this.selectedTimetable = selectedTimetable;
    }

    public Long getSelectedTimetableId() {
        return selectedTimetableId;
    }

    public void setSelectedTimetableId(Long selectedTimetableId) {
        this.selectedTimetableId = selectedTimetableId;
    }

    public void prepareEdit(Timetable timetable) {
        this.selectedTimetable = timetable;
        this.selectedTimetableId = timetable.getId();
        System.out.println("Preparing to edit: " + selectedTimetableId); // Add this line

    }

    public void cancelEdit() {
        selectedTimetable = null;
        selectedTimetableId = null;
    }

}