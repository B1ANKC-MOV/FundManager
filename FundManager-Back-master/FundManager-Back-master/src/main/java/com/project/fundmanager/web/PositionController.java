package com.project.fundmanager.web;

import com.project.fundmanager.entity.Fund;
import com.project.fundmanager.entity.Position;
import com.project.fundmanager.entity.TransactionRecord;
import com.project.fundmanager.entity.User;
import com.project.fundmanager.service.FundService;
import com.project.fundmanager.service.PositionService;
import com.project.fundmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    PositionService positionService;

    @Autowired
    FundService fundService;

    @Autowired
    UserService userService;


    @GetMapping("/getSelfPosition")
    public Map<String, Object> getSelfPosition(@SessionAttribute long id){
        try {
            return Map.of("success",positionService.getByUId(id));
        } catch (Exception e) {
            return Map.of("error",  e.getMessage());
        }
    }

    @PostMapping("/changePosition")
    public Map<String,Object> changePosition(@RequestBody Map<String,String> postData,@SessionAttribute(name = "id") long uid){// id 为uid
        long fid = Long.parseLong(postData.get("fid"));
        double amount = Double.parseDouble(postData.get("amount")); //此处为份额而不是金额
        if(amount ==0){
            return Map.of("error", "Meet Zero Amount");
        }
        Position pos = positionService.getByUIdAndFId(uid,fid);
        Fund toBuy = fundService.getFundById(fid);
        User theUser = userService.getUserById(uid);
        if(pos == null && amount < 0){ // 无持仓还想卖
            return Map.of("error", "don't have position");
        }else if (amount >0 && amount*toBuy.getCurrentPrice()> theUser.getBalance()){ // 想买但买不起
            return Map.of("error", "Lack of balance");
        }else if(amount <0 &&pos.getCount() < -amount){ // 想卖但不够
            return Map.of("error", "Insufficient position");
        }else if(pos == null){ // 新pos
            theUser.setBalance(theUser.getBalance() - amount*toBuy.getCurrentPrice());
            pos = new Position(uid,fid,amount,amount*toBuy.getCurrentPrice());
            positionService.addNewPosition(pos);
            return Map.of("success", "OK,new Position");
        }else{ //update 旧pos
            theUser.setBalance(theUser.getBalance() - amount*toBuy.getCurrentPrice());
            pos.setCount(pos.getCount()+amount);
            pos.setHaveCost(pos.getHaveCost()+amount*toBuy.getCurrentPrice());
            positionService.updatePosition(pos,amount);
            return Map.of("success", "OK,updated Position");
        }
    }
}
