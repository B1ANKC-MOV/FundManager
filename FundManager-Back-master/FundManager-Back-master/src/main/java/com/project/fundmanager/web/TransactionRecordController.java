package com.project.fundmanager.web;

import com.project.fundmanager.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@RestController
@RequestMapping("/transactionRecord")
public class TransactionRecordController {

    @Autowired
    TransactionRecordService transactionRecordService;

    @GetMapping("/getMyTransactionRecords")
    public Map<String, Object> getMyTransactionRecords(@SessionAttribute("id")long uid){
        try {
            return Map.of("success",transactionRecordService.getByUId(uid));
        }catch (Exception e){
            return Map.of("error",  e.getMessage());
        }
    }
}
