package controller;

import domain.Role;
import domain.User;
import service.UserService;
import util.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Principal;
import java.util.Set;

@ManagedBean(name = "loginBean", eager = true)
public class LoginBean {
    private String username;
    private String password;

    @Inject
    UserService userService;

    public LoginBean() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(ActionEvent event) throws IOException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

            try {
                boolean successful = userService.login(username, password);
                request.login(username, password);

                if (!userService.login(username, password)) {
                    // Login failed, return this to the user and stop
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Login", "A user with this username or password could not be found"));
                    return;
                }

                // We can safely fetch this now because the login function has returned true at this point
                User user = userService.getUserByName(username);

                externalContext.getSessionMap().put("user", user);
                externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");

            } catch (ServletException e) {
                // Handle unknown username / password in request.login()
                Logger.log(e);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Login", "A user with this username or password could not be found"));
            }
        }
        catch (Exception ex) {
            Logger.log(ex);
        }
    }

    public void logout(){
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.invalidateSession();
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        }
        catch (Exception x) {
            Logger.log(x);
        }
    }
}
