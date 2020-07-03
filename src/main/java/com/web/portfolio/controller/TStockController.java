package com.web.portfolio.controller;

import com.web.portfolio.entity.Classify;
import com.web.portfolio.entity.TStock;
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
 * 股票、指數、匯率
 * @author user
 */

@RestController
@RequestMapping("/portfolio/tstock")
public class TStockController {
    
    @PersistenceContext
    protected EntityManager em;
    
    @GetMapping(value = {"/","/query"})
    public List<TStock> query(){
        Query query = em.createQuery("SELECT t FROM TStock t");
        List<TStock> list = query.getResultList();
        return list;
    }
    
    @GetMapping(value = {"/{id}", "/get/{id}"})
    @Transactional
    public TStock get(@PathVariable("id") Long id){
        TStock tstock = em.find(TStock.class, id);
        return tstock;
    }
    
    @PostMapping(value = {"/","/add"})
    @Transactional
    public TStock add(@RequestBody Map<String, String> map){
        Classify classify = em.find(Classify.class, map.get("classify_id"));
        TStock tstock = new TStock();
        tstock.setName(map.get("name"));
        tstock.setSymbol(map.get("symbol"));
        tstock.setClassify(classify);
        em.persist(tstock);
        em.flush();
        return tstock;
    }
    
    @PutMapping(value = {"/{id}", "/update/{id}"})
    @Transactional
    public Boolean update(@PathVariable("id") Long id, @RequestBody Map<String, String> map){
        TStock o_TStock = em.find(TStock.class, id);
        if (o_TStock == null) {
            return false;
        }
        
        Classify classify = em.find(Classify.class, map.get("classify_id"));
        o_TStock.setName(map.get("name"));
        o_TStock.setSymbol(map.get("symbol"));
        o_TStock.setClassify(classify);
        
        em.persist(o_TStock);
        em.flush();
        
        return true;
    }
    
    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    @Transactional
    public Boolean delete(@PathVariable("id") Long id){
        TStock tStock = em.find(TStock.class, id);
        em.remove(tStock);    
        return true;

    }
}
