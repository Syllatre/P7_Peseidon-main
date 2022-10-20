package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.CurvePoint;
import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.repositories.CurvePointRepository;
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
public class CurveServiceTest {

    @InjectMocks
    private CurveService curveService;
    @Mock
    private CurvePointRepository curvePointRepository;

    private Optional<CurvePoint> curvePoint;

    @BeforeEach
    private void init() {
        curvePoint = Optional.of(new CurvePoint(1, 10.0, 20.0));
    }

    @AfterAll
    private void deletedCurve() {
        curvePointRepository.delete(curvePoint.get());
    }

    @Test
    void updateTest() {
        when(curvePointRepository.findById(anyInt())).thenReturn(curvePoint);
        CurvePoint updateCurvePoint = curvePoint.get();
        updateCurvePoint.setTerm(20.0);
        when(curvePointRepository.save(updateCurvePoint)).thenReturn(updateCurvePoint);

        curveService.updateCurvePoint(1, curvePoint.get());
        assertEquals(updateCurvePoint.getTerm(), 20.0);
        assertTrue(curveService.updateCurvePoint(1, curvePoint.get()));
    }

    @Test
    void updateFailTest() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
        CurvePoint updateCurvePoint = curvePoint.get();
        updateCurvePoint.setValue(30.0);

        curveService.updateCurvePoint(1, curvePoint.get());
        assertFalse(curveService.updateCurvePoint(1, curvePoint.get()));
    }

    @Test
    void createTest() {
        when(curvePointRepository.save(curvePoint.get())).thenReturn(curvePoint.get());
        curveService.create(curvePoint.get());

        verify(curvePointRepository, times(1)).save(curvePoint.get());
    }

    @Test
    void deleteTest() {
        CurvePoint curvePoint1 = curvePoint.get();
        curvePoint1.setId(1);
        curveService.delete(curvePoint1.getId());

        verify(curvePointRepository).deleteById(any());
    }

    @Test
    void findAll() {
        List<CurvePoint> curvePointArrayList = new ArrayList<>();
        curvePointArrayList.add(curvePoint.get());
        when(curvePointRepository.findAll()).thenReturn(curvePointArrayList);
        assertEquals(curvePointRepository.findAll().size(), 1);
    }

    @Test
    void finByIdTest() {
        when(curvePointRepository.findById(anyInt())).thenReturn(curvePoint);
        CurvePoint curvePoint1 = curveService.findById(1);
        assertEquals(curvePoint1.getTerm(), 10.0);
    }
}
