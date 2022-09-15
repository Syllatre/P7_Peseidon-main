package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RuleNameService {

    private RuleNameRepository ruleNameRepository;

    public List<RuleName> findAll(){
        return ruleNameRepository.findAll();
    }

    public RuleName create (RuleName ruleName){
        return ruleNameRepository.save(ruleName);
    }

    public RuleName update (RuleName ruleName){
        return ruleNameRepository.save(ruleName);
    }

    public void delete(int id){
        ruleNameRepository.deleteById(id);
    }

    public RuleName findById(int id){
        return ruleNameRepository.findById(id).get();
    }
}
