package com.project.fundmanager.mapper;

import com.project.fundmanager.entity.FavoriteRecord;
import com.project.fundmanager.entity.Position;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FavoriteRecordMapper {
    @Select("Select * FROM \"favoriteRecord\" WHERE id =${id}")
    FavoriteRecord getById(@Param("id") long id);

    @Select("Select * FROM \"favoriteRecord\" WHERE uid =${uid}")
    List<FavoriteRecord> getListByUId(@Param("uid") long uid);

    @Select("Select * FROM \"favoriteRecord\" WHERE fid =${fid}")
    List<FavoriteRecord> getListByFId(@Param("fid") long fid);

    @Select("Select * FROM \"favoriteRecord\"")
    List<FavoriteRecord> getList();

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO \"favoriteRecord\" (fid, uid) VALUES (#{record.fid}, #{record.uid})")
    int insert(@Param("record") FavoriteRecord record);

    @Delete("DELETE FROM \"favoriteRecord\" WHERE id = #{id}")
    int deleteById(@Param("id") long id);


    @Delete("DELETE FROM \"favoriteRecord\" WHERE uid = #{uid} AND fid = #{fid}")
    int deleteByUIdAndFId(@Param("uid") long uid,@Param("fid") long fid);
}
