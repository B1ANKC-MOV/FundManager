package com.project.fundmanager.service;

import com.project.fundmanager.entity.Position;
import com.project.fundmanager.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PositionService {

    @Autowired
    PositionMapper positionMapper;

    @Autowired
    TransactionRecordService transactionRecordService;

    @Autowired
    FundService fundService;

    @Autowired
    UserService userService;

    public Position getById(long id){
        return positionMapper.getById(id);
    }

    public List<Position> getByUId(long uid){
        return positionMapper.getByUId(uid);
    }

    public Position getByUIdAndFId(long uid,long fid){
        return positionMapper.getByUIdAndFId(uid,fid);
    }

    public void addNewPosition(Position position){
        double price= fundService.getFundById(position.getFid()).getCurrentPrice();
        if(positionMapper.insert(position) == 0){
            throw new RuntimeException("SQL didn't modified");
        }
        userService.updateUserBalance(position.getUid(),-position.getCount()*price,2,false);
        transactionRecordService.addTransactionRecord(position.getUid(),position.getFid(),position.getCount(),price);
    }

    public void updatePosition(Position position,double amount){
        double price= fundService.getFundById(position.getFid()).getCurrentPrice();
        if(positionMapper.update(position) == 0){
            throw new RuntimeException("SQL didn't modified");
        }
        userService.updateUserBalance(position.getUid(),-amount*price,2,false);
        transactionRecordService.addTransactionRecord(position.getUid(),position.getFid(),amount,fundService.getFundById(position.getFid()).getCurrentPrice());
    }
}
