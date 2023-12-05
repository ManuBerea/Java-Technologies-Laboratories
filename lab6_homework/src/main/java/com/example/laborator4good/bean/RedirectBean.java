package com.example.laborator4good.bean;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
@Data
public class RedirectBean {

    public void redirectToViewDataPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("dataView.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToEditDataPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("dataEdit.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToAssignPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("assign.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
