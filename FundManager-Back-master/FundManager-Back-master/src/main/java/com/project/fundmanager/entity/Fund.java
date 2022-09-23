package com.project.fundmanager.entity;

import java.util.Date;

public class Fund {
    private int id;
    private double currentPrice;
    private String type;
    private String name;
    private String fullName;
    private String managerName;
    private Date releaseDate;
    private String InvestmentObjectives;
    private String InvestmentScope;
    private String InvestmentStrategy;

    public Fund(int id, double currentPrice, String type, String name, String fullName, String managerName, Date releaseDate, String investmentObjectives, String investmentScope, String investmentStrategy) {
        this.id = id;
        this.currentPrice = currentPrice;
        this.type = type;
        this.name = name;
        this.fullName = fullName;
        this.managerName = managerName;
        this.releaseDate = releaseDate;
        InvestmentObjectives = investmentObjectives;
        InvestmentScope = investmentScope;
        InvestmentStrategy = investmentStrategy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getInvestmentObjectives() {
        return InvestmentObjectives;
    }

    public void setInvestmentObjectives(String investmentObjectives) {
        InvestmentObjectives = investmentObjectives;
    }

    public String getInvestmentScope() {
        return InvestmentScope;
    }

    public void setInvestmentScope(String investmentScope) {
        InvestmentScope = investmentScope;
    }

    public String getInvestmentStrategy() {
        return InvestmentStrategy;
    }

    public void setInvestmentStrategy(String investmentStrategy) {
        InvestmentStrategy = investmentStrategy;
    }
}
