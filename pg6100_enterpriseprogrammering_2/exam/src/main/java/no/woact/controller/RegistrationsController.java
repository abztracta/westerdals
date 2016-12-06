package no.woact.controller;

import no.woact.dao.UserDao;
import no.woact.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Model
public class RegistrationsController implements Serializable {

    private static final Logger LOG = Logger.getLogger(RegistrationsController.class.getName());

    @Inject
    private UserDao userDao;

    private User user;

    @PostConstruct
    public void init() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String createNewUser(SessionsController sessionsController) {
        User existingUser = userDao.getByUsername(user.getUsername());
        if (existingUser == null && user.matchingPasswordConfirmation()) {
            sessionsController.signIn(user);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("success", "You have registered a user!");
            userDao.persist(user);
            return "home.xhtml?faces-redirect=true";
        }
        return "newUser.xhtml";
    }
}
