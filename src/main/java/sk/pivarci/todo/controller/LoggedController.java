package sk.pivarci.todo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import sk.pivarci.todo.model.User;
import sk.pivarci.todo.security.UserPrincipal;

public abstract class LoggedController {

    protected User currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser")
            return ((UserPrincipal) principal).getUser();
        else return null;
    }

}
