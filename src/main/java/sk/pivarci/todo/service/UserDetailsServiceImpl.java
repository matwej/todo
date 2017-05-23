package sk.pivarci.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sk.pivarci.todo.model.User;
import sk.pivarci.todo.repo.UserRepository;
import sk.pivarci.todo.security.UserPrincipal;

import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByLogin(username);
        return new UserPrincipal(user.getLogin(), user.getPassword(), new HashSet<GrantedAuthority>(), user);
    }
}
