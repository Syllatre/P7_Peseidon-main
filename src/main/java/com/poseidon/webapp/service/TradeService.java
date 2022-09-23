package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.Trade;
import com.poseidon.webapp.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    private TradeRepository tradeRepository;

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Trade create(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade update(Trade trade) {
        return tradeRepository.save(trade);
    }

    public void delete(int id) {
        tradeRepository.deleteById(id);
    }

    public Trade findById(int id) {
        return tradeRepository.findById(id).get();
    }
}
