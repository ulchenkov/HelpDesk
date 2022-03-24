package org.launchcode.helpdesk.user;

import org.launchcode.helpdesk.data.UserRepository;
import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("No user found with username: %s", username));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                user.isActive(),
                /*accountNonExpired*/ true,
                /*credentialsNonExpired*/ true,
                /*accountNonLocked*/ true,
                getAuthorities(user.getRoles())
                );
    }

    private static List<GrantedAuthority> getAuthorities (List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getSpringRole()));
        }
        return authorities;
    }
}
