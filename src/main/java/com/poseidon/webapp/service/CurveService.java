package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.CurvePoint;
import com.poseidon.webapp.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CurveService {
    CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }

    public CurvePoint create(CurvePoint curvePoint){
        return curvePointRepository.save(curvePoint);
    }

    public CurvePoint findById(int id){
        return curvePointRepository.findById(id).get();
    }

    public CurvePoint update(CurvePoint curvePoint){
        return curvePointRepository.save(curvePoint);
    }

    public void delete(int id){
        curvePointRepository.deleteById(id);
    }
}
