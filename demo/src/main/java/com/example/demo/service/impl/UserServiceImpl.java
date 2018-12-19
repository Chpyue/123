package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: demo
 * @Package: com.example.demo.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: yueChunPeng
 * @CreateDate: 2018-12-19 13:32
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018-12-19 13:32
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User getUser(int id) {
        return userMapper.selectByPrimaryKey(id);
    }




}
