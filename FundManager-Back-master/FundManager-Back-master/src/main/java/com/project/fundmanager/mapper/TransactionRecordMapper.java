package com.project.fundmanager.mapper;

import com.project.fundmanager.entity.TransactionRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionRecordMapper {
    @Select("SELECT * FROM \"transactionRecords\" WHERE uid = #{uid}")
    List<TransactionRecord> getByUId(@Param("uid") long uid);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO \"transactionRecords\" (tdate, amount, price, type,fid,uid) VALUES (#{tR.tdate}, #{tR.amount}, #{tR.price}, #{tR.type},#{tR.fid},#{tR.uid})")
    int insert(@Param("tR") TransactionRecord tR);
}
