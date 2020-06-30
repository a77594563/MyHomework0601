package com.web.portfolio.controller;

import com.web.portfolio.entity.TStock;
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
 * 觀察股
 * @author user
 */

@RestController
@RequestMapping("/portfolio/watch")
public class WatchController {
    
    @PersistenceContext
    protected EntityManager em;
    
    @GetMapping(value = {"/", "/query"})
    public List<Watch> query(){
        Query query = em.createQuery("SELECT W FROM Watch W");
        List<Watch> list = query.getResultList();
        return list;
    }
    
    @GetMapping(value = {"/{id}", "/get/{id}"})
    @Transactional
    public Watch get(@PathVariable("id") Long id){
        Watch watch = em.find(Watch.class, id);
        watch.gettStocks().size(); // 因為 @ManyToMany 預設資料載入是 Lazy, 所以加入此行可取得 tStocks 資料
        return watch;
    }
    
    @PutMapping(value = {"/{id}", "/update/{id}"})
    public Boolean update(@PathVariable("id") Long id, @RequestBody Map<String, String> map){
        Watch o_watch = get(id);
        if (o_watch == null) {
            return false;
        }
        o_watch.setName(map.get("name"));
        em.persist(o_watch);
        em.flush();
        return true;
    }
    
    @GetMapping(value = {"/{id}/add/{tstock_id}"})
    @Transactional
    public Watch add_tstock(@PathVariable("id") Long id,@PathVariable("tstock_id") Long tstock_id){
        TStock tstock = em.find(TStock.class, tstock_id);
        Watch watch = get(id);
        watch.addtStock(tstock);
        em.persist(watch);
        em.flush();
        return get(id);
    }
    
    @DeleteMapping(value = {"/{id}/remove/{tstock_id}"})
    @Transactional
    public Watch remove_tstock(@PathVariable("id") Long id, @PathVariable("tstock_id") Long tstock_id){
        TStock tstock = em.find(TStock.class, tstock_id);
        Watch watch = get(id);
        watch.removetStock(tstock);
        em.persist(watch);
        em.flush();
        return get(id);
    }
}
