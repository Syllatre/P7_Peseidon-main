package com.poseidon.webapp.controller.IT;

import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.service.TradeService;
import com.poseidon.webapp.service.UserServiceImp;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource({"/applicationtest.properties"})
@Sql(scripts = "/poseidontest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserServiceImp userServiceImp;

    @Test
    @WithMockUser(username = "admin", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "ADMIN")
    @Order(1)
    public void displayUserList() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "ADMIN")
    @Order(2)
    void addUser() throws Exception {
        User user1 = new User("username","Password10*","fullname","USER");
        mockMvc.perform(post("/user/add")
                        .param("username", user1.getUsername())
                        .param("fullname", user1.getFullname())
                        .param("password", user1.getPassword())
                        .param("role", user1.getRole())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/add?success"));

        User userSaved = userServiceImp.findById(3).get();
        assertEquals(userSaved.getFullname(),"fullname");
    }

    @Test
    @WithMockUser(username = "admin", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "ADMIN")
    @Order(3)
    void updateUser() throws Exception {
        User user1 = new User("username","Password10*","fullname","USER");

        mockMvc.perform(post("/user/update/3")
                        .param("username", "akira")
                        .param("fullname", user1.getFullname())
                        .param("password", user1.getPassword())
                        .param("role", user1.getRole())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/list"));

        User userUpdate = userServiceImp.findById(3).get();
        assertEquals(userUpdate.getUsername(),"akira");
    }
//    @Test
//    @WithMockUser(username = "admin", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "ADMIN")
//    @Order(4)
//    void deleteUser() throws Exception {
//
//        mockMvc.perform(get("/user/delete/3"))
//                .andExpect(redirectedUrl("/user/list"));
//    }
}
