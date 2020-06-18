package com.web.portfolio;

import com.web.portfolio.entity.Classify;
import com.web.portfolio.entity.Investor;
import com.web.portfolio.entity.JPAUtil;
import com.web.portfolio.entity.TStock;
import com.web.portfolio.entity.Watch;
import javax.persistence.EntityManager;

/**
 * 資料庫初始設定值
 * @author user
 */
public class Test {
    
    private static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    
    public static void main(String[] args) {
        Investor investor1 = new Investor("John", "1234", "John@java.com", 1000000);
        Investor investor2 = new Investor("Mary", "1234", "Mary@java.com", 1000000);
        Investor investor3 = new Investor("admin", "1111", "admin@java.com", 1000000);
        // 因之後設計有 google 信箱認證,所以之後要在 Investor 的 Pass 項目修改為 1
        
        Classify classify01 = new Classify("股票", true);
        Classify classify02 = new Classify("匯率", true);
        Classify classify03 = new Classify("指數", false);
        
        TStock ts1 = new TStock("2330.TW", "台積電", classify01);
        TStock ts2 = new TStock("2317.TW", "鴻海", classify01);
        TStock ts3 = new TStock("1101.TW", "台泥", classify01);
        
        TStock ts4 = new TStock("USDTWD=x", "美金台幣", classify02);
        TStock ts5 = new TStock("JPYTWD=x", "日幣台幣", classify02);
        TStock ts6 = new TStock("CNYTWD=x", "人民幣台幣", classify02);
        
        TStock ts7 = new TStock("^TWII", "台灣加權", classify03);
        TStock ts8 = new TStock("^IXIC", "納斯達克", classify03);
        TStock ts9 = new TStock("^DJI", "道瓊工業", classify03);
        
        Watch watch = new Watch();
        watch.addtStock(ts1);
        watch.addtStock(ts2);
        watch.addtStock(ts4);
        watch.addtStock(ts6);
        watch.addtStock(ts7);
        watch.addtStock(ts9);
        watch.setInvestor(investor1);
        watch.setName("我的觀察股");
        
        em.getTransaction().begin();
        em.persist(ts1);
        em.persist(ts2);
        em.persist(ts3);
        em.persist(ts4);
        em.persist(ts5);
        em.persist(ts6);
        em.persist(ts7);
        em.persist(ts8);
        em.persist(ts9);
        em.persist(investor1);
        em.persist(investor2);
        em.persist(investor3);
        em.persist(watch);
        em.getTransaction().commit();
        
        System.out.println("資料庫初始寫入ok!");
    }
}
