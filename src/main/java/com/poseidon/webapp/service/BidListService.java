package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BidListService {
    private BidListRepository bidListRepository;

    public BidList create(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    public void delete(int bidListId) {
        bidListRepository.deleteById(bidListId);
    }

    public BidList findById(int id) {
        Optional<BidList> findById = bidListRepository.findById(id);
        return findById.get();
    }

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public Boolean updateBidList(int id, BidList bidList) {

        Optional<BidList> listBidList = bidListRepository.findById(id);
        if (listBidList.isPresent()) {
            BidList updateBidList = listBidList.get();
            updateBidList.setAccount(bidList.getAccount());
            updateBidList.setType(bidList.getType());
            updateBidList.setBidQuantity(bidList.getBidQuantity());
            bidListRepository.save(updateBidList);
            log.debug("BidList " + id + " was updated");
            return true;
        }
        log.debug("the update was failed because the id: " + id + " doesn't exist");
        return false;
    }
}
