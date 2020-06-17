package com.web.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Watch implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "invetor_id", referencedColumnName = "id")
    @JsonIgnoreProperties("watchs")
    private Investor investor;
    
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "watch_tstock",
            joinColumns = {
                @JoinColumn(name = "watch_id",
                            nullable = false, updatable = false)
            },
            inverseJoinColumns = {@JoinColumn(name = "tstock_id",
                            nullable = false, updatable = false)
            }
    )
    private Set<TStock> tstocks = new LinkedHashSet<>();

    public Watch() {
    }

    public Watch(String name, Investor investor) {
        this.name = name;
        this.investor = investor;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public Set<TStock> getTstocks() {
        return tstocks;
    }

    public void setTstocks(Set<TStock> tstocks) {
        this.tstocks = tstocks;
    }
    
    private Set<TStock> addTStock(TStock tStock){
        tstocks.add(tStock);
        return tstocks;
    }
    
    private Set<TStock> removeTStock(TStock tStock){
        tstocks.remove(tStock);
        return tstocks;
    }
}
