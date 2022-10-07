package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.Rating;
import com.poseidon.webapp.domain.RuleName;
import com.poseidon.webapp.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RuleNameService {

    private RuleNameRepository ruleNameRepository;

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public RuleName create(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public void delete(int id) {
        ruleNameRepository.deleteById(id);
    }

    public RuleName findById(int id) {
        return ruleNameRepository.findById(id).get();
    }

    public Boolean updateRuleName(Integer id, RuleName ruleName) {
        Optional<RuleName> ruleNameExist = ruleNameRepository.findById(id);
        if (ruleNameExist.isPresent()) {
            RuleName updateRuleName = ruleNameExist.get();
            updateRuleName.setName(ruleName.getName());
            updateRuleName.setDescription(ruleName.getDescription());
            updateRuleName.setJson(ruleName.getJson());
            updateRuleName.setTemplate(ruleName.getTemplate());
            updateRuleName.setSqlStr(ruleName.getSqlStr());
            updateRuleName.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(updateRuleName);
            log.info("Rating with id " + id + " is updated as " + updateRuleName);
            return true;
        }
        log.debug("the update was failed because the id: " + id + " doesn't exist");
        return false;
    }
}
