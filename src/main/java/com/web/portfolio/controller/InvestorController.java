package com.web.portfolio.controller;

import com.web.portfolio.entity.Investor;
import com.web.portfolio.entity.Watch;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帳號管理維修
 *
 * @author user
 */
@RestController
@RequestMapping("/portfolio/investor")
public class InvestorController {

    @PersistenceContext
    protected EntityManager em;

    @GetMapping(value = {"/", "/query"})
    public List<Investor> query() {
        Query query = em.createQuery("Select i from Investor i");
        List<Investor> list = query.getResultList();
        return list;
    }

    @GetMapping(value = {"/{id}", "/get/{id}"})
    public Investor get(@PathVariable("id") long id) {
        Investor investor = em.find(Investor.class, id);

        if (investor != null && investor.getPortfolios() != null && investor.getPortfolios().size() > 0) {
            investor.getPortfolios().size();
        }
        if (investor != null && investor.getWatchs() != null && investor.getWatchs().size() > 0) {
            investor.getWatchs().size();
        }

        return investor;
    }

    @PostMapping(value = {"/", "/add"})
    @Transactional
    public Investor add(@RequestBody Map<String, String> map) {
        Investor investor = new Investor();
        investor.setUsername(map.get("username"));
        investor.setPassword(map.get("password"));
        investor.setEmail(map.get("email"));
        investor.setBalance(Integer.parseInt(map.get("balance")));
        Watch watch = new Watch("我的投資組合", investor);

        em.persist(investor);
        em.persist(watch);
        em.flush();
        //取得最新 id
        Long id = investor.getId();

        return investor;
    }

    @PutMapping(value = {"/{id}", "/update/{id}}"})
    @Transactional
    public Boolean update(@PathVariable("id") Long id, @RequestBody Map<String, String> map) {
        Investor o_Investor = get(id);
        if (o_Investor == null) {
            return false;
        }
        o_Investor.setUsername(map.get("username"));
        o_Investor.setPassword(map.get("password"));
        o_Investor.setEmail(map.get("email"));
        o_Investor.setBalance(Integer.parseInt(map.get("balance")));
        em.persist(o_Investor);
        em.flush();
        return true;
    }

    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    @Transactional
    public Boolean delete(@PathVariable("id") Long id) {
        em.remove(get(id));
        em.flush();
        return get(id) == null ? true : false;
    }
}
