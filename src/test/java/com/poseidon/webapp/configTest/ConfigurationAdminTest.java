package com.poseidon.webapp.configTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
@TestConfiguration
public class ConfigurationAdminTest {
    @Bean
    public UserDetailsService userDetailsServiceUser() {
        User user = new User("admin", "Password10*", Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        return new InMemoryUserDetailsManager(Arrays.asList(user));
    }
}
