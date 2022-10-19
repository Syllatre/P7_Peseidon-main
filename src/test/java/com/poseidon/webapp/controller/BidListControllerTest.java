package com.poseidon.webapp.controller;

import com.poseidon.webapp.configTest.ConfigurationTest;
import com.poseidon.webapp.controllers.BidListController;
import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.service.BidListService;
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
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BidListController.class)
@Import(ConfigurationTest.class)
public class BidListControllerTest {

    private BidList bidList1;

    private BidList bidList2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @BeforeEach
    public void setUp() {
        bidList1 = new BidList("account1", "type1", 10.0);
        bidList2 = new BidList("account2", "type2", 11.0);
        bidList1.setBidListId(1);
        bidList2.setBidListId(2);
    }

    @Test
    @WithUserDetails("aimen")
    public void displayBidListList() throws Exception {
        when(bidListService.findAll()).thenReturn(Arrays.asList(bidList1, bidList2));
        mockMvc.perform(get("/bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());

        verify(bidListService).findAll();
    }

    @Test
    @WithUserDetails("aimen")
    public void displayCurvePointForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("aimen")
    void addValideBidlist() throws Exception {
        when(bidListService.create(bidList1)).thenReturn(bidList1);
        when(bidListService.findAll()).thenReturn(Arrays.asList(bidList2));

        mockMvc.perform(post("/bidList/validate")
                        .param("account", bidList1.getAccount())
                        .param("type", bidList1.getType())
                        .param("bidQuantity", bidList1.getBidQuantity().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).create(any(BidList.class));
    }

    @Test
    @WithUserDetails("aimen")
    void addValideBidlistWithError() throws Exception {
        when(bidListService.create(bidList1)).thenReturn(bidList1);
        when(bidListService.findAll()).thenReturn(Arrays.asList(bidList2));

        mockMvc.perform(post("/bidList/validate")
                        .param("account", bidList1.getAccount())
                        .param("type", "")
                        .param("bidQuantity", bidList1.getBidQuantity().toString())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/add"))
                .andReturn();

        verify(bidListService, times(0)).create(any(BidList.class));
    }

    @Test
    @WithUserDetails("aimen")
    public void showUpdateFormBidList() throws Exception {
        when(bidListService.findById(1)).thenReturn(bidList1);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk());

        verify(bidListService).findById(1);
    }

    @Test
    @WithUserDetails("aimen")
    void updateBidList() throws Exception {
        when(bidListService.updateBidList(1, bidList1)).thenReturn(true);

        mockMvc.perform(post("/bidList/update/1")
                        .param("account", bidList1.getAccount())
                        .param("type", bidList1.getType())
                        .param("bidQuantity", bidList1.getBidQuantity().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).updateBidList(anyInt(), any(BidList.class));
    }

    @Test
    @WithUserDetails("aimen")
    void wrongUpdateBidList() throws Exception {
        when(bidListService.updateBidList(1, bidList1)).thenReturn(true);

        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "")
                        .param("type", bidList1.getType())
                        .param("bidQuantity", bidList1.getBidQuantity().toString())
                        .with(csrf()))
                .andExpect(view().name("bidList/update"))
                .andReturn();

        verify(bidListService, times(0)).updateBidList(anyInt(), any(BidList.class));
    }

    @Test
    @WithUserDetails("aimen")
    void deleteBidList() throws Exception {
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).delete(1);
    }
}
