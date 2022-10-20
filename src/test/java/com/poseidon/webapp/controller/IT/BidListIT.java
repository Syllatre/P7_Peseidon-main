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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class BidListIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BidListService bidListService;


    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    public void displayBidListListIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Test
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

        BidList bidListSaved = bidListService.findById(1);
        assertEquals(bidListSaved.getAccount(), "account1");
        bidListService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void updateBidList() throws Exception {
        BidList bidList1 = new BidList("account1", "type1", 10.0);
        bidListService.create(bidList1);
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "account2")
                        .param("type", bidList1.getType())
                        .param("bidQuantity", bidList1.getBidQuantity().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/bidList/list"));

        BidList bidListUpdate = bidListService.findById(1);
        assertEquals(bidListUpdate.getAccount(), "account2");

        bidListService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void deleteBidList() throws Exception {
        BidList bidList1 = new BidList("account1", "type1", 10.0);
        bidListService.create(bidList1);

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
