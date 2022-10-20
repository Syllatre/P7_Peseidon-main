package com.poseidon.webapp.controller.IT;

import com.poseidon.webapp.domain.Trade;
import com.poseidon.webapp.service.TradeService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource({"/applicationtest.properties"})
@Sql(scripts = "/poseidontest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TradeIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TradeService tradeService;

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Order(1)
    public void displayTradeList() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(model().attributeExists("tradeList"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Order(2)
    void addValideTradeWithError() throws Exception {
        Trade trade1 = new Trade("account", "type");

        mockMvc.perform(post("/trade/validate")
                        .param("account", trade1.getAccount())
                        .param("type", trade1.getType())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/trade/list"));

        Trade tradeSaved = tradeService.findById(1);
        assertEquals(tradeSaved.getAccount(), "account");
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Order(3)
    void updateTrade() throws Exception {
        Trade trade1 = new Trade("account", "type");

        mockMvc.perform(post("/trade/update/1")
                        .param("account", "account10")
                        .param("type", trade1.getType())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/trade/list"));

        Trade tradeUpdate = tradeService.findById(1);
        assertEquals(tradeUpdate.getAccount(), "account10");
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    @Order(4)
    void deleteTrade() throws Exception {
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(redirectedUrl("/trade/list"));
    }
}
