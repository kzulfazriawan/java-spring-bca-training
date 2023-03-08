package com.springdemo.securities;

import com.springdemo.entities.User;
import com.springdemo.entities.UserGroup;
import com.springdemo.repository.UserRepositoryInterface;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserSecurityDetail implements UserDetailsService {
    private UserRepositoryInterface userRepository;

    public UserSecurityDetail(UserRepositoryInterface userRepository){
        this.userRepository = userRepository;
    }

    private Collection<? extends GrantedAuthority> mapUserGroupToAuth(Collection <UserGroup> userGroups) {
        Collection<? extends GrantedAuthority> userGroup = userGroups.stream().map( group -> new SimpleGrantedAuthority(group.getName()))
                .collect(Collectors.toList());
        return userGroup;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // email sebagai username
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), this.mapUserGroupToAuth(user.getGroups()));
        } else {
            throw new UsernameNotFoundException("Credentials are mismatch");
        }
    }
}
