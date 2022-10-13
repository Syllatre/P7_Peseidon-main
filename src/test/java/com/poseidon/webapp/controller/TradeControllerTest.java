package com.poseidon.webapp.controller;

import com.poseidon.webapp.configTest.ConfigurationTest;
import com.poseidon.webapp.controllers.TradeController;
import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.domain.Trade;
import com.poseidon.webapp.service.RuleNameService;
import com.poseidon.webapp.service.TradeService;
import com.poseidon.webapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(TradeController.class)
@Import(ConfigurationTest.class)
public class TradeControllerTest {
    private Trade trade1;

    private Trade trade2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TradeService tradeService;

    @BeforeEach
    public void setUp() {
        trade1 = new Trade("account", "type");
        trade2 = new Trade("account", "type");
        trade1.setTradeId(1);
        trade2.setTradeId(2);
    }

    @Test
    @WithUserDetails("aimen")
    public void displayTradeList() throws Exception {
        when(tradeService.findAll()).thenReturn(Arrays.asList(trade1, trade2));
        mockMvc.perform(get("/trade/list"))
                .andExpect(model().attributeExists("tradeList"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

        verify(tradeService).findAll();
    }

    @Test
    @WithUserDetails("aimen")
    public void displayAddForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("aimen")
    void addValideTrade() throws Exception {
        when(tradeService.create(trade1)).thenReturn(trade1);
        when(tradeService.findAll()).thenReturn(Arrays.asList(trade2));

        mockMvc.perform(post("/trade/validate")
                        .param("account", trade1.getAccount())
                        .param("type", trade1.getType())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).create(any(Trade.class));
    }

    @Test
    @WithUserDetails("aimen")
    void addValideTradeWithError() throws Exception {
        when(tradeService.create(trade1)).thenReturn(trade1);
        when(tradeService.findAll()).thenReturn(Arrays.asList(trade2));

        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", trade1.getType())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/add"))
                .andReturn();

        verify(tradeService, times(0)).create(any(Trade.class));
    }

    @Test
    @WithUserDetails("aimen")
    public void showUpdateFormTrade() throws Exception {
        when(tradeService.findById(1)).thenReturn(trade1);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk());

        verify(tradeService).findById(1);
    }

    @Test
    @WithUserDetails("aimen")
    void updateCurvePoint() throws Exception {
        when(tradeService.updateTrade(1, trade1)).thenReturn(true);

        mockMvc.perform(post("/trade/update/1")
                        .param("account", trade1.getAccount())
                        .param("type", trade1.getType())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).updateTrade(anyInt(), any(Trade.class));
    }

    @Test
    @WithUserDetails("aimen")
    void wrongUpdateCurvePoint() throws Exception {
        when(tradeService.updateTrade(1, trade1)).thenReturn(true);

        mockMvc.perform(post("/trade/update/1")
                        .param("account", "")
                        .param("type", trade1.getType())
                        .with(csrf()))
                .andExpect(view().name("trade/update"))
                .andReturn();

        verify(tradeService, times(0)).updateTrade(anyInt(), any(Trade.class));
    }

    @Test
    @WithUserDetails("aimen")
    void deleteCurvePoint() throws Exception {
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).delete(1);
    }
}
