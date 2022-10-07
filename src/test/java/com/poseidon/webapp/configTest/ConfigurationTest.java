package com.poseidon.webapp.configTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class ConfigurationTest {
    @Bean
    public UserDetailsService userDetailsService() {
        User basicUser = new User("aimen", "Password10*", Arrays.asList(new SimpleGrantedAuthority("USER")));
        return new InMemoryUserDetailsManager(Arrays.asList(basicUser));
    }
}
