package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.domain.Trade;
import com.poseidon.webapp.repositories.TradeRepository;
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
public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;
    @Mock
    private TradeRepository tradeRepository;

    private Optional<Trade> trade;

    @BeforeEach
    private void init() {
        trade = Optional.of(new Trade("account", "type"));
    }

    @AfterAll
    private void deletedBidList() {
        tradeRepository.delete(trade.get());
    }

    @Test
    void updateTest() {
        when(tradeRepository.findById(anyInt())).thenReturn(trade);
        Trade updateTrade = trade.get();
        updateTrade.setAccount("account1");
        when(tradeRepository.save(updateTrade)).thenReturn(updateTrade);

        tradeService.updateTrade(1, trade.get());
        assertEquals(updateTrade.getAccount(), "account1");
        assertTrue(tradeService.updateTrade(1, trade.get()));
    }
    @Test
    void updateFailTest() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());
        Trade updateTrade = trade.get();
        updateTrade.setAccount("akira");

        tradeService.updateTrade(1, trade.get());
        assertFalse(tradeService.updateTrade(1, trade.get()));
    }

    @Test
    void createTest() {
        when(tradeRepository.save(trade.get())).thenReturn(trade.get());
        tradeService.create(trade.get());

        verify(tradeRepository, times(1)).save(trade.get());
    }

    @Test
    void deleteTest() {
        Trade trade1 = trade.get();
        trade1.setTradeId(1);
        tradeService.delete(trade1.getTradeId());

        verify(tradeRepository).deleteById(any());
    }

    @Test
    void findAll() {
        List<Trade> tradeArrayList = new ArrayList<>();
        tradeArrayList.add(trade.get());
        when(tradeRepository.findAll()).thenReturn(tradeArrayList);
        assertEquals(tradeRepository.findAll().size(), 1);
    }

    @Test
    void finByIdTest() {
        when(tradeRepository.findById(anyInt())).thenReturn(trade);
        Trade trade1 = tradeService.findById(1);
        assertEquals(trade1.getAccount(), "account");
    }
}
