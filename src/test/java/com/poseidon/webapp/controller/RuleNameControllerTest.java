package com.poseidon.webapp.controller;

import com.poseidon.webapp.configTest.ConfigurationTest;
import com.poseidon.webapp.controllers.RuleNameController;
import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.service.RuleNameService;
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

@WebMvcTest(RuleNameController.class)
@Import(ConfigurationTest.class)
public class RuleNameControllerTest {
    private RuleName ruleName1;

    private RuleName ruleName2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @BeforeEach
    public void setUp() {
        ruleName1 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleName2 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleName1.setId(1);
        ruleName2.setId(2);
    }

    @Test
    @WithUserDetails("aimen")
    public void displayRuleNameList() throws Exception {
        when(ruleNameService.findAll()).thenReturn(Arrays.asList(ruleName1, ruleName2));
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(model().attributeExists("ruleNameList"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());

        verify(ruleNameService).findAll();
    }

    @Test
    @WithUserDetails("aimen")
    public void displayAddForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("aimen")
    void addValideRuleName() throws Exception {
        when(ruleNameService.create(ruleName1)).thenReturn(ruleName1);
        when(ruleNameService.findAll()).thenReturn(Arrays.asList(ruleName2));

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", ruleName1.getName())
                        .param("description", ruleName1.getDescription())
                        .param("json", ruleName1.getJson())
                        .param("template", ruleName1.getTemplate())
                        .param("sqlStr", ruleName1.getSqlStr())
                        .param("sqlPart", ruleName1.getSqlPart())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).create(any(RuleName.class));
    }

    @Test
    @WithUserDetails("aimen")
    void addValideRatingWithError() throws Exception {
        when(ruleNameService.create(ruleName1)).thenReturn(ruleName1);
        when(ruleNameService.findAll()).thenReturn(Arrays.asList(ruleName2));

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", ruleName1.getDescription())
                        .param("json", ruleName1.getJson())
                        .param("template", ruleName1.getTemplate())
                        .param("sqlStr", ruleName1.getSqlStr())
                        .param("sqlPart", ruleName1.getSqlPart())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("ruleName/add"))
                .andReturn();

        verify(ruleNameService, times(0)).create(any(RuleName.class));
    }

    @Test
    @WithUserDetails("aimen")
    public void showUpdateFormRating() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(ruleName1);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(view().name("ruleName/update"))
                .andExpect(status().isOk());

        verify(ruleNameService).findById(1);
    }

    @Test
    @WithUserDetails("aimen")
    void updateCurvePoint() throws Exception {
        when(ruleNameService.updateRuleName(1, ruleName1)).thenReturn(true);

        mockMvc.perform(post("/ruleName/update/1")
                        .param("name", "name")
                        .param("description", ruleName1.getDescription())
                        .param("json", ruleName1.getJson())
                        .param("template", ruleName1.getTemplate())
                        .param("sqlStr", ruleName1.getSqlStr())
                        .param("sqlPart", ruleName1.getSqlPart())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).updateRuleName(anyInt(), any(RuleName.class));
    }

    @Test
    @WithUserDetails("aimen")
    void wrongUpdateCurvePoint() throws Exception {
        when(ruleNameService.updateRuleName(1, ruleName1)).thenReturn(true);

        mockMvc.perform(post("/ruleName/update/1")
                        .param("name", "")
                        .param("description", "")
                        .param("json", ruleName1.getJson())
                        .param("template", ruleName1.getTemplate())
                        .param("sqlStr", ruleName1.getSqlStr())
                        .param("sqlPart", ruleName1.getSqlPart())
                        .with(csrf()))
                .andExpect(view().name("ruleName/update"))
                .andReturn();

        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RuleName.class));
    }

    @Test
    @WithUserDetails("aimen")
    void deleteCurvePoint() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).delete(1);
    }
}
