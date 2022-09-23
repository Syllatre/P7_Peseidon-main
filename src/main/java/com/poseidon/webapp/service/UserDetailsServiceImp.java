package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.MyUserPrincipal;
import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user)).build();
        return userDetails;
    }
        private Collection<GrantedAuthority> getAuthorities(User user) {
            Collection<GrantedAuthority> authorities = new ArrayList<>(2);
            if (user.getRole().equals("ADMIN")) {
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            } else if (user.getRole().equals("USER")) {
                authorities.add(new SimpleGrantedAuthority("USER"));
            }
            return authorities;
        }
}
