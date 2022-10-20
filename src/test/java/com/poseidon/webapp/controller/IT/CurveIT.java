package com.poseidon.webapp.controller.IT;

import com.poseidon.webapp.domain.CurvePoint;
import com.poseidon.webapp.service.CurveService;
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
public class CurveIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurveService curveService;

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    public void displayCurvePointList_1() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(model().attributeExists("curveList"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void addValideCurvePoint_2() throws Exception {
        CurvePoint curve1 = new CurvePoint(1, 2.0, 10.0);
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", curve1.getCurveId().toString())
                        .param("term", curve1.getTerm().toString())
                        .param("value", curve1.getValue().toString())
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
        CurvePoint curvePointSaved = curveService.findById(1);
        assertEquals(curvePointSaved.getId(), 1);

        curveService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void updateCurvePoint_3() throws Exception {
        CurvePoint curve1 = new CurvePoint(1, 2.0, 10.0);
        curveService.create(curve1);
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", curve1.getTerm().toString())
                        .param("value", "143.0")
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/curvePoint/list"));

        assertEquals(curveService.findById(1).getValue(), 143.0);

        curveService.delete(1);
    }

    @Test
    @WithMockUser(username = "user", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void deleteCurvePoint_4() throws Exception {
        CurvePoint curve1 = new CurvePoint(1, 2.0, 10.0);
        curveService.create(curve1);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
