package no.nith.pg5100.controller;

import no.nith.pg5100.dto.Subject;
import no.nith.pg5100.dto.User;
import no.nith.pg5100.dto.UserType;
import no.nith.pg5100.infrastructure.user.JpaUser;
import no.nith.pg5100.infrastructure.user.UserDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Model
public class UserController {
    @Inject @JpaUser
    private UserDao userDao;

    private int selectedId;
    private User user;
    private FacesContext context;
    private Flash flash;

    @PostConstruct
    public void init() {
        user = new User();
        context = FacesContext.getCurrentInstance();
        flash = context.getExternalContext().getFlash();
    }

    public void persistNewUser() throws IOException {
        if (userDao.persist(user) != null) {
            flash.put("created", "User created successfully");
            context.getExternalContext().redirect(context.getExternalContext().getApplicationContextPath() + "/user/users.jsf");
        }
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void initUser() {
        user = userDao.findById(selectedId);
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SelectItem> getUserTypes() {
        return Arrays.asList(UserType.values()).stream().map(t -> new SelectItem(t, t.getLabel())).collect(Collectors.toList());
    }

    public List<String> getSelectedSubjects() {
        return user.getSubjects().stream().map(Subject::getName).collect(Collectors.toList());
    }
}
