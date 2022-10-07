package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.repositories.RuleNameRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RuleNameServiceTest {

    @InjectMocks
    private RuleNameService ruleNameService;
    @Mock
    private RuleNameRepository ruleNameRepository;

    private Optional<RuleName> ruleName;

    @BeforeEach
    private void init() {
        ruleName = Optional.of(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
    }

    @AfterAll
    private void deletedBidList() {
        ruleNameRepository.delete(ruleName.get());
    }

    @Test
    void updateTest() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(ruleName);
        RuleName updateRuleName = ruleName.get();
        updateRuleName.setName("akira");
        when(ruleNameRepository.save(updateRuleName)).thenReturn(updateRuleName);

        ruleNameService.updateRuleName(1, ruleName.get());
        assertEquals(updateRuleName.getName(), "akira");
        assertTrue(ruleNameService.updateRuleName(1, ruleName.get()));
    }

    @Test
    void updateFailTest() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());
        RuleName updateRuleName = ruleName.get();
        updateRuleName.setName("akira");

        ruleNameService.updateRuleName(1, ruleName.get());
        assertFalse(ruleNameService.updateRuleName(1, ruleName.get()));
    }

    @Test
    void createTest() {
        when(ruleNameRepository.save(ruleName.get())).thenReturn(ruleName.get());
        ruleNameService.create(ruleName.get());

        verify(ruleNameRepository, times(1)).save(ruleName.get());
    }

    @Test
    void deleteTest() {
        RuleName ruleName1 = ruleName.get();
        ruleName1.setId(1);
        ruleNameService.delete(ruleName1.getId());

        verify(ruleNameRepository).deleteById(any());
    }

    @Test
    void findAll() {
        List<RuleName> ruleNameArrayList = new ArrayList<>();
        ruleNameArrayList.add(ruleName.get());
        when(ruleNameRepository.findAll()).thenReturn(ruleNameArrayList);
        assertEquals(ruleNameRepository.findAll().size(), 1);
    }

    @Test
    void finByIdTest() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(ruleName);
        RuleName ruleName1 = ruleNameService.findById(1);
        assertEquals(ruleName1.getSqlPart(), "sqlPart");
    }
}
