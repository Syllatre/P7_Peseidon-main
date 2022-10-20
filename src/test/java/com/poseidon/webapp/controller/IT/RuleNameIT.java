package com.poseidon.webapp.controller.IT;

import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.service.RuleNameService;
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
public class RuleNameIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RuleNameService ruleNameService;

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    public void displayRuleNameList() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(model().attributeExists("ruleNameList"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void addValideRuleName() throws Exception {
        RuleName ruleName1 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");

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

        RuleName ruleNameSaved = ruleNameService.findById(1);
        assertEquals(ruleNameSaved.getName(), "name");

        ruleNameService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void updateCurvePoint() throws Exception {
        RuleName ruleName1 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleNameService.create(ruleName1);

        mockMvc.perform(post("/ruleName/update/1")
                        .param("name", "akira")
                        .param("description", ruleName1.getDescription())
                        .param("json", ruleName1.getJson())
                        .param("template", ruleName1.getTemplate())
                        .param("sqlStr", ruleName1.getSqlStr())
                        .param("sqlPart", ruleName1.getSqlPart())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/ruleName/list"));

        RuleName ruleNameUpdate = ruleNameService.findById(1);
        assertEquals(ruleNameUpdate.getName(), "akira");

        ruleNameService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void deleteCurvePoint() throws Exception {
        RuleName ruleName1 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleNameService.create(ruleName1);

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
