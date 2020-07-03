package com.web.portfolio.controller;


import com.web.portfolio.entity.TStock;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * 股票.匯率.指數 價格抓取
 *
 * @author user
 */
@RestController
@RequestMapping("/portfolio/price")
public class PriceController {

    @PersistenceContext
    protected EntityManager em;

    @GetMapping("/refresh")
    @Transactional
    public List<TStock> refresh() {
        Query query = em.createQuery("SELECT t FROM TStock T");
            List<TStock> list = query.getResultList();
            for (TStock tStock : list) {
                // 取得報價資訊
                try {
                    Stock stock = YahooFinance.get(tStock.getSymbol());
                    tStock.setChange(stock.getQuote().getChange());
                    tStock.setChangeInPercent(stock.getQuote().getChangeInPercent());
                    tStock.setPreClosed(stock.getQuote().getPreviousClose());
                    tStock.setPrice(stock.getQuote().getPrice());
                    tStock.setTransactionDate(stock.getQuote().getLastTradeTime().getTime());
                    tStock.setVolumn(stock.getQuote().getVolume());

                    // 更新報價
                    em.persist(tStock);
                    em.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
            return list;
    }
    
    @GetMapping(value = {"/histquotes/{symbol:.+}"}) // 歷史股價
    public List<HistoricalQuote> queryHistQuotes(@PathVariable("symbol") String symbol) {
        List<HistoricalQuote> histQuotes = null;
        try {
            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            from.add(Calendar.MONTH, -1);

            Stock google = YahooFinance.get(symbol);
            histQuotes = google.getHistory(from, to, Interval.DAILY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return histQuotes;
    }
}
