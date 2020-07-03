package com.web.portfolio.controller;

import com.web.portfolio.entity.Investor;
import com.web.portfolio.entity.Portfolio;
import com.web.portfolio.entity.TStock;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 買進 賣出
 * @author user
 */

@RestController
@RequestMapping("/portfolio/order")
public class OrderController {
    
    @PersistenceContext
    protected EntityManager em;
    
    @GetMapping("/buy/{tstock_id}/{amount}")
    @Transactional
    public String buy(HttpSession session,
                    @PathVariable("tstock_id") String tstock_id,
                    @PathVariable("amount") Integer amount){
        Investor login_investor = (Investor)session.getAttribute("investor");
        Investor investor = em.find(Investor.class, login_investor.getId());
        if( investor == null){
            return "Investor is null";
        }
        
        TStock tstock = em.find(TStock.class, tstock_id);
        if( tstock == null){
            return "Tstock is null";
        }
        
        int buyTotalCost = (int)(tstock.getPrice().doubleValue() * amount);
        int balance = investor.getBalance() - buyTotalCost;
        if (balance < 0) {
            return "Insufficient balance";
        }
        investor.setBalance(balance);
        
        Portfolio p = new Portfolio();
        p.setInvestor(investor);
        p.settStock(tstock);
        p.setCost(tstock.getPrice().doubleValue());
        p.setAmount(amount);
        p.setDate(new Date());
        
        em.persist(investor);
        em.persist(p);
        em.flush();
        
        return p.getId() + "";
    }
    
    // 賣出
    @GetMapping(value = {"/sell/{id}/{amount}"})
    @Transactional
    public String sell(HttpSession session, @PathVariable("id") Long id, @PathVariable("amount") Integer amount) {
        Investor login_investor = (Investor) session.getAttribute("investor");
        Investor investor = em.find(Investor.class, login_investor.getId());
        if (investor == null) {
            return "Investor None";
        }

        Portfolio po = em.find(Portfolio.class, id);
        if (po == null) {
            return "Portfolio None";
        }

        po.setAmount(po.getAmount() - amount);

        // 回滾利潤
        int profit = (int) (amount * po.gettStock().getPrice().doubleValue());
        investor.setBalance(investor.getBalance() + profit);

        em.persist(po);
        em.persist(investor);

        em.flush();
        return "OK";
    }
}
