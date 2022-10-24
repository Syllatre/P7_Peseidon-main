package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.CurvePoint;
import com.poseidon.webapp.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CurveService {
    CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    public CurvePoint create(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    public CurvePoint findById(int id) {
        return curvePointRepository.findById(id).get();
    }

    public void delete(int id) {
        curvePointRepository.deleteById(id);
    }

    public boolean updateCurvePoint(int id, CurvePoint curvePoint) {
        Optional<CurvePoint> curvePointExist = curvePointRepository.findById(id);
        if (curvePointExist.isPresent()) {
            CurvePoint curvePointUpdate = curvePointExist.get();
            curvePointUpdate.setCurveId(curvePoint.getCurveId());
            curvePointUpdate.setTerm(curvePoint.getTerm());
            curvePointUpdate.setValue(curvePoint.getValue());
            curvePointRepository.save(curvePointUpdate);
            log.debug("CurvePoint " + id + " was updated");
            return true;
        }
        log.debug("the update was failed because the id: " + id + " doesn't exist");
        return false;
    }
}
