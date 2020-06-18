
package com.web.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Investor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;
    
    @Column
    private String userName;
    
    @Column
    private String password;
    
    @Column
    private Integer balance;
    
    @Column
    private String code;
    
    @Column
    private Boolean pass;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "investor", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("investor")
    private Set<Portfolio> portfolios;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "investor", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("investor")
    private Set<Watch> watchs;

    public Investor() {
    }

    public Investor(String userName, String password, String email,  Integer balance) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Set<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Set<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public Set<Watch> getWatchs() {
        return watchs;
    }

    public void setWatchs(Set<Watch> watchs) {
        this.watchs = watchs;
    }

    @Override
    public String toString() {
        return "Investor{" + "id=" + id + ", email=" + email + ", userName=" + userName + ", password=" + password + ", balance=" + balance + ", code=" + code + ", pass=" + pass + '}';
    }
    
}
