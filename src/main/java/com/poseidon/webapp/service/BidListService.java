package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BidListService {
    private BidListRepository bidListRepository;

    public BidList update(BidList bidList){
        return bidListRepository.save(bidList);
    }

    public BidList create(BidList bidList){
        return bidListRepository.save(bidList);
    }

    public void delete(int bidListId){
        bidListRepository.deleteById(bidListId);
    }

public BidList findById(int id){
    Optional<BidList> findById = bidListRepository.findById(id);
    return findById.get();
}
    public List<BidList> findAll(){
        return bidListRepository.findAll();
    }
}
