package com.project.fundmanager.service;

import com.project.fundmanager.entity.Fund;
import com.project.fundmanager.mapper.FundMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FundService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FundMapper fundMapper;

    public Fund getFundById(long id){
        return fundMapper.getById(id);
    }

    public List<Fund> getFundList(int offset, int maxResults){
        return  fundMapper.getAll(offset,maxResults);
    }

}
