package com.poseidon.webapp.controller;

import com.poseidon.webapp.configTest.ConfigurationTest;
import com.poseidon.webapp.controllers.CurveController;
import com.poseidon.webapp.domain.CurvePoint;
import com.poseidon.webapp.service.CurveService;
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

@WebMvcTest(CurveController.class)
@Import(ConfigurationTest.class)
public class CurveControllerTest {

    private CurvePoint curve1;

    private CurvePoint curve2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CurveService curveService;

    @BeforeEach
    public void setUp() {
        curve1 = new CurvePoint(1, 2.0, 10.0);
        curve2 = new CurvePoint(2, 3.0, 10.0);
        curve1.setId(1);
        curve2.setId(2);
    }

    @Test
    @WithUserDetails("aimen")
    public void displayCurvePointList() throws Exception {
        when(curveService.findAll()).thenReturn(Arrays.asList(curve1, curve2));
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(model().attributeExists("curveList"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());

        verify(curveService).findAll();
    }

    @Test
    @WithUserDetails("aimen")
    public void displayAddForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("aimen")
    void addValideCurvePoint() throws Exception {
        when(curveService.create(curve1)).thenReturn(curve1);
        when(curveService.findAll()).thenReturn(Arrays.asList(curve2));

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", curve1.getCurveId().toString())
                        .param("term", curve1.getTerm().toString())
                        .param("value", curve1.getValue().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curveService).create(any(CurvePoint.class));
    }

    @Test
    @WithUserDetails("aimen")
    void addValideCurvePointWithError() throws Exception {
        when(curveService.create(curve1)).thenReturn(curve1);
        when(curveService.findAll()).thenReturn(Arrays.asList(curve2));

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "")
                        .param("term", curve1.getTerm().toString())
                        .param("value", curve1.getValue().toString())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/add"))
                .andReturn();

        verify(curveService, times(0)).create(any(CurvePoint.class));
    }

    @Test
    @WithUserDetails("aimen")
    public void showUpdateFormCurvePoint() throws Exception {
        when(curveService.findById(1)).thenReturn(curve1);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(status().isOk());

        verify(curveService).findById(1);
    }

    @Test
    @WithUserDetails("aimen")
    void updateCurvePoint() throws Exception {
        when(curveService.updateCurvePoint(1, curve1)).thenReturn(true);

        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", curve1.getTerm().toString())
                        .param("value", curve1.getValue().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curveService).updateCurvePoint(anyInt(), any(CurvePoint.class));
    }

    @Test
    @WithUserDetails("aimen")
    void wrongUpdateCurvePoint() throws Exception {
        when(curveService.updateCurvePoint(1, curve1)).thenReturn(true);

        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "")
                        .param("term", curve1.getTerm().toString())
                        .param("value", curve1.getValue().toString())
                        .with(csrf()))
                .andExpect(view().name("curvePoint/update"))
                .andReturn();

        verify(curveService, times(0)).updateCurvePoint(anyInt(), any(CurvePoint.class));
    }

    @Test
    @WithUserDetails("aimen")
    void deleteCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curveService).delete(1);
    }
}
