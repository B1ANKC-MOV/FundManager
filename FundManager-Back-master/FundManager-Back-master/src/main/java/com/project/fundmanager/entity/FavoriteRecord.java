package com.project.fundmanager.entity;

public class FavoriteRecord {
    private long id;
    private long fid;
    private long uid;

    public FavoriteRecord(long fid, long uid) {
        this.fid = fid;
        this.uid = uid;
    }

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

    public FavoriteRecord(long id, long uid, long fid) {
        this.id = id;
        this.uid = uid;
        this.fid = fid;
    }
}
