package com.project.fundmanager.entity;

import java.util.Date;

public class TransactionRecord {
    private long id;
    private Date tdate;
    private double amount;
    private double price;
    private int type;
    private long fid;

    public TransactionRecord(Date tdate, double amount, double price, int type, long fid, long uid) {
        this.tdate = tdate;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.fid = fid;
        this.uid = uid;
    }

    public TransactionRecord(long id, Date tdate, double amount, double price, int type, long fid, long uid) {
        this.id = id;
        this.tdate = tdate;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.fid = fid;
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTdate() {
        return tdate;
    }

    public void setTdate(Date tdate) {
        this.tdate = tdate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    private long uid;
}
