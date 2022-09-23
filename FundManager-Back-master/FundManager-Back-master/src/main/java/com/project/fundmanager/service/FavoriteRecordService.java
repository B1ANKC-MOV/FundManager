package com.project.fundmanager.service;

import com.project.fundmanager.entity.FavoriteRecord;
import com.project.fundmanager.mapper.FavoriteRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FavoriteRecordService {
    @Autowired
    FavoriteRecordMapper favoriteRecordMapper;

    public List<FavoriteRecord> getByUId(long uid){
        return favoriteRecordMapper.getListByUId(uid);
    }

    public int addFavorite(long uid,long fid){
        for (FavoriteRecord record:
             this.getByUId(uid)) {
            if (record.getFid()==fid){
                throw new RuntimeException("Have added favorite.");
            }
        }
        int res =favoriteRecordMapper.insert(new FavoriteRecord(fid,uid));
        return res;
    }
    public int removeFavorite(long uid,long fid){
        int res =favoriteRecordMapper.deleteByUIdAndFId(uid,fid);
        return res;
    }
}
