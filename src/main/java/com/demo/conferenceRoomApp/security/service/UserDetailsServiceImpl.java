package com.demo.conferenceRoomApp.security.service;
import com.demo.conferenceRoomApp.security.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      UserDetails  userDetails=   userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       /* if (principal instanceof UserDetails) {
            ((UserDetails) principal).getAuthorities().add(new SimpleGrantedAuthority("ROLE_FOO") {
            });
        }*/
        return new User(userDetails.getUsername(), userDetails.getPassword(), true, true, true, true, new ArrayList<>() {
        });

    }
}
