package com.poseidon.webapp.controller;

import com.poseidon.webapp.configTest.ConfigurationAdminTest;
import com.poseidon.webapp.controllers.UserController;
import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.service.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(ConfigurationAdminTest.class)
public class UserControllerAdminTest {
    private User user1;

    private User user2;

    private Optional<User> user3;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImp userService;

    @BeforeEach
    public void setUp() {
        user1 = new User("username","Password10*","fullname","USER");
        user2 = new User("username","Password20*","fullname","ADMIN");
        user3 =Optional.of( new User("username", "Password10*","fullname", "USER"));
        user1.setId(1);
        user2.setId(2);
        user3.get().setId(3);

    }


    @Test
    @WithUserDetails("admin")
    public void displayUserList() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
        mockMvc.perform(get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());

        verify(userService).findAll();
    }

    @Test
    @WithUserDetails("admin")
    public void displayAddForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    void addUser() throws Exception {
        when(userService.create(user1)).thenReturn(user1);
        when(userService.findAll()).thenReturn(Arrays.asList(user2));

        mockMvc.perform(post("/user/add")
                        .param("username", user1.getUsername())
                        .param("fullname", user1.getFullname())
                        .param("password", user1.getPassword())
                        .param("role", user1.getRole())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService).create(any(User.class));
    }

    @Test
    @WithUserDetails("admin")
    void addUserWithError() throws Exception {
        when(userService.create(user1)).thenReturn(user1);
        when(userService.findAll()).thenReturn(Arrays.asList(user2));

        mockMvc.perform(post("/user/add")
                        .param("username", "username")
                        .param("fullname", user1.getFullname())
                        .param("password", user1.getPassword())
                        .param("role", "")
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"))
                .andReturn();

        verify(userService, times(0)).create(any(User.class));
    }


    @Test
    @WithUserDetails("admin")
    void updateUser() throws Exception {
        when(userService.update(user1)).thenReturn(user1);

        mockMvc.perform(post("/user/update/1")
                        .param("username", "username")
                        .param("fullname", user1.getFullname())
                        .param("password", user1.getPassword())
                        .param("role", user1.getRole())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).update(any(User.class));
    }

    @Test
    @WithUserDetails("admin")
    void wrongUpdateUser() throws Exception {
        when(userService.update(user1)).thenReturn(user1);

        mockMvc.perform(post("/user/update/1")
                        .param("username", "username")
                        .param("fullname", user1.getFullname())
                        .param("password", user1.getPassword())
                        .param("role", "")
                        .with(csrf()))
                .andExpect(view().name("user/update"))
                .andReturn();

        verify(userService, times(0)).update(any(User.class));
    }

    @Test
    @WithUserDetails("admin")
    void deleteUser() throws Exception {
        when(userService.findById(3)).thenReturn(user3);
        mockMvc.perform(get("/user/delete/3"))
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).delete(any(User.class));
    }
}
