package controller;

import domain.User;
import service.UserService;
import util.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ManagedBean(name = "indexController", eager = true)
public class IndexController {
    private String username;
    private String password;

    @Inject
    UserService userService;

    public IndexController() { }

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

    public void login() throws IOException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

            try {
                request.login(username, password);

                if (!userService.login(username, password)) {
                    // Login failed, return this to the user and stop
                    context.addMessage(null, new FacesMessage("A user with this username or password could not be found"));
                    return;
                }

                // We can safely fetch this now because the login function has returned true at this point
                User user = userService.getUserByName(username);
                externalContext.getSessionMap().put("user", user);
                externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");

            } catch (ServletException e) {
                // Handle unknown username / password in request.login()
                Logger.log(e);
                context.addMessage(null, new FacesMessage("A user with this username or password could not be found"));
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
