package com.project.fundmanager.service;

import com.project.fundmanager.entity.TransactionRecord;
import com.project.fundmanager.mapper.TransactionRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TransactionRecordService {
    @Autowired
    TransactionRecordMapper transactionRecordMapper;

    public List<TransactionRecord> getByUId(long uid){
        return transactionRecordMapper.getByUId(uid);
    }

    public void addTransactionRecord(long uid,double amount){
        TransactionRecord toAdd = new TransactionRecord(new Date(),amount,0,1,0,uid);
        if(transactionRecordMapper.insert(toAdd)==0){
            throw new RuntimeException("SQL didn't modified");
        }
    }

    public void addTransactionRecord(long uid,long fid,double amount,double price){
        TransactionRecord toAdd = new TransactionRecord(new Date(),amount,price,0,fid,uid);
        if(transactionRecordMapper.insert(toAdd)==0){
            throw new RuntimeException("SQL didn't modified");
        }
    }
}
