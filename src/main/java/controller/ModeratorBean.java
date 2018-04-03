package controller;

import Exceptions.InvalidActionException;
import Exceptions.KweetNotFoundException;
import Exceptions.UserNotFoundException;
import domain.Kweet;
import domain.User;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import service.KweetService;
import service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@javax.faces.bean.ManagedBean(name = "moderatorBean", eager = true)
@SessionScoped
public class ModeratorBean {

    @Inject
    private UserService userService;

    @Inject
    private KweetService kweetService;

    private User selectedUser;
    private Kweet selectedKweet;

    private List<Kweet> selectedUserKweets;

    public Kweet getSelectedKweet() { return selectedKweet; }
    public void setSelectedKweet(Kweet selectedKweet) {
        this.selectedKweet = selectedKweet;
    }

    public User getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getUsers() {
        return userService.getUsers();
    }

    public List<Kweet> getSelectedUserKweets() {
        if (selectedUser != null) {
            try {
                return kweetService.getKweetsByUser(selectedUser.getUsername());
            } catch (UserNotFoundException e) {
                return new ArrayList<>();
            }
        }

        return new ArrayList<>();
    }

    public void setSelectedUserKweets(List<Kweet> selectedUserKweets) {
        this.selectedUserKweets = selectedUserKweets;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("User Selected", Integer.toString(((User) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("currently selected user: " + selectedUser.getDisplayname()));
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("User Unselected", Integer.toString(((User) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteKweet(Kweet kweet) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        User user = (User) session.getAttribute("user");
        try {
            kweetService.deleteKweet(user, kweet.getId());
        } catch (InvalidActionException | KweetNotFoundException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to delete kweet", e.getMessage());
            facesContext.addMessage(null, msg);
        }
    }
}
