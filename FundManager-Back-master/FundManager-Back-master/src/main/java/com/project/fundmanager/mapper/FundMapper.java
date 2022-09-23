package com.project.fundmanager.mapper;

import com.project.fundmanager.entity.Fund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FundMapper {

    @Select("SELECT id,\"currentPrice\",type,name,\"fullName\",\"managerName\",\"releaseDate\",\"InvestmentObjectives\",\"InvestmentScope\",\"InvestmentStrategy\" FROM fund WHERE id= #{id}")
    Fund getById(@Param("id") long id);

    @Select("SELECT id,\"currentPrice\",type,name,\"fullName\",\"managerName\",\"releaseDate\",\"InvestmentObjectives\",\"InvestmentScope\",\"InvestmentStrategy\" FROM fund OFFSET #{offset} LIMIT #{maxResults}")
    List<Fund> getAll(@Param("offset") int offset, @Param("maxResults") int maxResults);
}
