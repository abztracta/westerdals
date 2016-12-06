package no.woact.controller;

import no.woact.dao.UserDao;
import no.woact.external.restful.RestcountriesEuService;
import no.woact.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Espen Rønning
 * @version 2016-05-27
 * @since 1.8
 */
@Named
@SessionScoped
public class SessionsController implements Serializable {

    private static final Logger LOG = Logger.getLogger(SessionsController.class.getName());

    @Inject
    private RestcountriesEuService countryService;
    @Inject
    private UserDao userDao;

    private String loginUsername;
    private String loginPassword;

    private boolean showEventsFromUsersCountry;

    private User currentUser;

    @PostConstruct
    public void init() {
        showEventsFromUsersCountry = true;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isShowEventsFromUsersCountry() {
        return showEventsFromUsersCountry;
    }

    public void setShowEventsFromUsersCountry(boolean showEventsFromUsersCountry) {
        this.showEventsFromUsersCountry = showEventsFromUsersCountry;
    }

    public void showEventsChanged(ValueChangeEvent e) {
        LOG.info("User clicked the country event view button.");
        showEventsFromUsersCountry = !showEventsFromUsersCountry;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        } catch (IOException ex) {
            LOG.severe("Something went wrong with JSF. " + ex.getMessage());
        }
    }

    public boolean isSignedIn() {
        return currentUser != null;
    }

    public void signIn(User user) {
        currentUser = user;
    }

    public String signOut() {
        currentUser = null;
        showEventsFromUsersCountry = true;
        return "home.xhtml?faces-redirect=true";
    }

    public List<SelectItem> getCountries() {
        return countryService.getCountries().stream().map(country -> new SelectItem(country, country)).collect(Collectors.toList());
    }

    public String signIn() {
        LOG.info(loginUsername + " made a sign in attempt.");
        User user = userDao.getByUsername(loginUsername);
        LOG.info(loginUsername + " the " + user + " exists.");
        if (user != null && user.isCorrectPassword(loginPassword)) {
            signIn(user);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("success", "You have successfully signed in!");
            return "home.xhtml?faces-redirect=true";
        }
        LOG.info(loginUsername + " could not sign in.");
        return "sign_in.xhtml";
    }
}
