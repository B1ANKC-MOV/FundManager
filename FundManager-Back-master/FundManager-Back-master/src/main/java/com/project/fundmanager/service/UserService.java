package com.project.fundmanager.service;

import com.project.fundmanager.entity.User;
import com.project.fundmanager.entity.slimUser;
import com.project.fundmanager.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired TransactionRecordService transactionRecordService;

    public User getUserById(long id) {
        return userMapper.getById(id);
    }

    public User getUserByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    public List<slimUser> getUserList(int offset, int maxResults){
        return userMapper.getAll(offset,maxResults);
    }

    public Boolean checkPassword(User toCheck,String password){
        return passwordEncoder.matches(password,toCheck.getPassword());
    }

    public User login(String email, String password) {
        logger.info("try login by {}...", email);
        User user = getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("login failed.");
        }
        if (checkPassword(user,password)) {
            return user;
        }
        throw new RuntimeException("login failed.");
    }

    public User register(String email, String password, String name) {
        logger.info("try register by {}...", email);
        if (userMapper.getByEmail(email)==null){
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setRegisteredAt(new Date(System.currentTimeMillis()));
            if(userMapper.insert(user)==0){
                throw new RuntimeException("SQL didn't modified");
            }
            return user;
        }else{
            throw new RuntimeException("The email have been registered");
        }
    }

    public void updateUserName(Long id, String name) {
        User user = getUserById(id);
        user.setName(name);
        if(userMapper.update(user)==0){
            throw new RuntimeException("SQL didn't modified");
        }
    }

    public void updateUserPassword(Long id, String password) {
        User user = getUserById(id);
        user.setPassword(passwordEncoder.encode(password));

        if(userMapper.update(user) == 0){
            throw new RuntimeException("SQL didn't modified");
        }
    }

    public void deleteUser(Long id) {
        if(userMapper.deleteById(id) == 0){
            throw new RuntimeException("SQL didn't modified");
        }
    }

    public double updateUserBalance(Long id,double balance,int type,Boolean needToRecord) {
        User user = getUserById(id);
        switch (type){
            case 1:{ // 直接修改
                if(balance<0){
                    throw new RuntimeException("The balance will be lower than 0.");
                }
                user.setBalance(balance);
            }break;
            case 2:{ // 作加减
                if(user.getBalance()+balance<0){
                    throw new RuntimeException("The balance will be lower than 0.");
                }
                if(needToRecord){
                    transactionRecordService.addTransactionRecord(id,balance);
                }
                user.setBalance(user.getBalance()+balance);
            }break;
        }
        if(userMapper.update(user) == 0){
            throw new RuntimeException("SQL didn't modified");
        }
        return user.getBalance();
    }
}
