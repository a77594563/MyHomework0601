package com.web.portfolio.controller;

import com.web.portfolio.entity.Classify;
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
 * 商品分類
 * @author user
 */

@RestController
@RequestMapping("/portfolio/classify")
public class ClassifyController {
    
    @PersistenceContext
    protected EntityManager em;
    
    @GetMapping(value = {"/", "query"})
    public List<Classify> query(){
        Query query = em.createQuery("SELECT c FROM Classify c");
        List<Classify> list = query.getResultList();
        return list;
    }
    
    @GetMapping(value = {"/{id}","/get/{id}"})
    public Classify get(@PathVariable("id") Long id){
        Classify classify = em.find(Classify.class, id);
        //!!!!!因為是延遲加載，通過執行size()這種方式所有子項
        if ( classify != null && classify.gettStocks() != null) {
            classify.gettStocks().size();
        }
        return classify;
    }
    
    @PostMapping(value = {"/", "/add"})
    @Transactional
    public Classify add(@RequestBody Map<String, String> map){
        Classify classify = new Classify();
        classify.setName(map.get("name"));
        
        
        // 因 transaction 在 jsp 裡是以 checkbox 回傳,故 有值則代表 true
        if(map.get("transaction") == null){
            classify.setTransaction(Boolean.FALSE);
        }else{
            classify.setTransaction(Boolean.TRUE);
        }
        em.persist(classify);
        em.flush();
        Long id = classify.getId();
        return get(id);
    }
    
    @PutMapping(value = {"/{id}", "/update/{id}"})
    @Transactional
    public Boolean update(@PathVariable("id") Long id,@RequestBody Map<String, String> map){
        Classify o_Classify = get(id);
        if (o_Classify == null) {
            return false;
        }
        o_Classify.setName(map.get("name"));
        if(map.get("transaction") == null)
            o_Classify.setTransaction(false);
        else
            o_Classify.setTransaction(true);
        em.persist(o_Classify);
        em.flush();
        return true;
    }
    
    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    @Transactional
    public Boolean delete(@PathVariable("id") Long id){
        em.remove(get(id));
        em.flush();
        return get(id) == null ? true : false;
    }
}
