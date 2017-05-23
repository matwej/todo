package sk.pivarci.todo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserPrincipal extends User {
    private final sk.pivarci.todo.model.User user;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities, sk.pivarci.todo.model.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, sk.pivarci.todo.model.User user) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public sk.pivarci.todo.model.User getUser() {
        return user;
    }
}
