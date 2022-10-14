package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.BidList;
import com.poseidon.webapp.repositories.BidListRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidListServiceTest {


    @InjectMocks
    private BidListService bidListService;
    @Mock
    private BidListRepository bidListRepository;

    private Optional<BidList> bidList;


    @BeforeEach
    private void init() {
        bidList = Optional.of(new BidList("110", "type", 1.0));
    }

    @AfterAll
    private void deletedBidList() {
        bidListRepository.delete(bidList.get());
    }

    @Test
    void updateTest() {
        when(bidListRepository.findById(anyInt())).thenReturn(bidList);
        BidList updateBidList = bidList.get();
        updateBidList.setBidQuantity(10.0);
        when(bidListRepository.save(updateBidList)).thenReturn(updateBidList);

        bidListService.updateBidList(1, bidList.get());
        assertEquals(updateBidList.getBidQuantity(), 10.0);
        assertTrue(bidListService.updateBidList(1, bidList.get()));
    }

    @Test
    void updateFailTest() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());
        BidList updateBidList = bidList.get();
        updateBidList.setBidQuantity(10.0);

        bidListService.updateBidList(1, bidList.get());
        assertFalse(bidListService.updateBidList(1, bidList.get()));
    }

    @Test
    void createTest() {
        when(bidListRepository.save(bidList.get())).thenReturn(bidList.get());
        bidListService.create(bidList.get());

        verify(bidListRepository, times(1)).save(bidList.get());
    }

    @Test
    void deleteTest() {
        BidList bidList1 = bidList.get();
        bidList1.setBidListId(1);
        bidListService.delete(bidList1.getBidListId());

        verify(bidListRepository).deleteById(any());
    }

    @Test
    void findAll() {
        List<BidList> bidsList = new ArrayList<>();
        bidsList.add(bidList.get());
        when(bidListRepository.findAll()).thenReturn(bidsList);
        assertEquals(bidListRepository.findAll().size(), 1);
    }

    @Test
    void finByIdTest() {
        when(bidListRepository.findById(anyInt())).thenReturn(bidList);
        BidList bidList1 = bidListService.findById(1);
        assertEquals(bidList1.getBidQuantity(), 1.0);
    }
}
