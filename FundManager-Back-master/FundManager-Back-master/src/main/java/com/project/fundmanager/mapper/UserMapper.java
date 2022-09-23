package com.project.fundmanager.mapper;

import java.util.Date;
import java.util.List;

import com.project.fundmanager.entity.User;
import com.project.fundmanager.entity.slimUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(@Param("id") long id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User getByEmail(@Param("email") String email);

    @Select("SELECT id,name FROM users OFFSET #{offset} LIMIT #{maxResults}")
    List<slimUser> getAll(@Param("offset") int offset, @Param("maxResults") int maxResults);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO users (email, password, name, registeredAt) VALUES (#{user.email}, #{user.password}, #{user.name}, #{user.registeredAt})")
    int insert(@Param("user") User user);

    @Update("UPDATE users SET name = #{user.name},password = #{user.password}, registeredAt = #{user.registeredAt}, balance=#{user.balance} WHERE id = #{user.id}")
    int update(@Param("user") User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(@Param("id") long id);
}
