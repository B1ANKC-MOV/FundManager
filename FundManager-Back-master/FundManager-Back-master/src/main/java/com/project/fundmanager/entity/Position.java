package com.project.fundmanager.entity;

public class Position {
    private long id;
    private long uid;
    private long fid;
    private double count;
    private double haveCost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getHaveCost() {
        return haveCost;
    }

    public void setHaveCost(double haveCost) {
        this.haveCost = haveCost;
    }

    public Position(long uid, long fid, double count, double haveCost) {
        this.uid = uid;
        this.fid = fid;
        this.count = count;
        this.haveCost = haveCost;
    }

    public Position(long id, long uid, long fid, double count, double haveCost) {
        this.id = id;
        this.uid = uid;
        this.fid = fid;
        this.count = count;
        this.haveCost = haveCost;
    }
}
