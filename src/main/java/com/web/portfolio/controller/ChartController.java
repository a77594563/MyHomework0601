package com.web.portfolio.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 資產配置
 * @author user
 */
@RestController
@RequestMapping("/portfolio/chart")
public class ChartController {
    
    @PersistenceContext
    protected EntityManager em;
    
    @GetMapping(value = {"/asset/{id}"})
    public List asset(@PathVariable("id") Long id){
        String sql = "SELECT c.name, SUM(p.amount * s.price) as subtotal "
                   + "FROM Classify c, Portfolio p, TStock s "
                   + "WHERE p.investor.id=:id AND p.tStock.id = s.id AND s.classify.id = c.id "
                   + "GROUP BY c.name";
        Query query = em.createQuery(sql);
        query.setParameter("id", id);
        List list = query.getResultList();
        System.out.println(list);
        return list;
    }
    
    @GetMapping(value = {"/profit/{id}"})
    public List profit(@PathVariable("id") Long id){
        String sql = "SELECT c.name, SUM(p.amount * (s.price-p.cost)) as subtotal "
                   + "FROM Classify c, Portfolio p, TStock s "
                   + "WHERE p.investor.id=:id AND p.tStock.id = s.id AND s.classify.id = c.id "
                   + "GROUP BY c.name";
        Query query = em.createQuery(sql);
        query.setParameter("id", id);
        List list = query.getResultList();
        System.out.println(list);
        return list;
    }
            
}
