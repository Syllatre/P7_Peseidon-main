package com.poseidon.webapp.controller.IT;

import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.service.BidListService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource({"/applicationtest.properties"})
@Sql(scripts = "/poseidontest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BidListIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BidListService bidListService;


    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Order(1)
    public void displayBidListListIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Test
    @Order(2)
    void addValideBidlist() throws Exception {
        BidList bidList1 = new BidList("account1", "type1", 10.0);

        mockMvc.perform(post("/bidList/validate")
                        .param("account", bidList1.getAccount())
                        .param("type", bidList1.getType())
                        .param("bidQuantity", bidList1.getBidQuantity().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Order(3)
    void updateBidList() throws Exception {
        BidList bidList1 = new BidList("account1", "type1", 10.0);
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "account1")
                        .param("type", bidList1.getType())
                        .param("bidQuantity", bidList1.getBidQuantity().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/bidList/list"));

    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Order(4)
    void deleteBidList() throws Exception {
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
