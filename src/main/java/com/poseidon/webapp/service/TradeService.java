package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.Trade;
import com.poseidon.webapp.repositories.TradeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TradeService {
    private TradeRepository tradeRepository;

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Trade create(Trade trade) {
        return tradeRepository.save(trade);
    }

    public void delete(int id) {
        tradeRepository.deleteById(id);
    }

    public Trade findById(int id, Trade trade) {
        return trade;
    }

    public Boolean updateTrade(Integer id, Trade trade) {
        Optional<Trade> tradeExist = tradeRepository.findById(id);
        if (tradeExist.isPresent()) {
            Trade updateTrade = tradeExist.get();
            updateTrade.setAccount(trade.getAccount());
            updateTrade.setType(trade.getType());
            updateTrade.setBuyQuantity(trade.getBuyQuantity());
            tradeRepository.save(updateTrade);
            log.info("Trade with id " + id + " is updated as " + updateTrade);
            return true;
        }
        log.debug("the update was failed because the id: " + id + " doesn't exist");
        return false;

    }
}
