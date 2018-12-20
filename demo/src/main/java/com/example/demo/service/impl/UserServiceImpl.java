package com.example.demo.service.impl;

import com.example.demo.mapper.UserAuthorityMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserAuthority;
import com.example.demo.model.UserExample;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;
    @Autowired
    private AuthorityService authorityService;

    @Override
    public List<User> getUserList() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria= example.createCriteria();
        return userMapper.selectByExample(example);
    }

    @Override
    public User saveUser(User user) {

        for (GrantedAuthority authority : user.getAuthorities()) {
            String name = authority.getAuthority();
            Integer authorityId = authorityService.getAuthorityByName(name).getAuthorityId();
            userAuthorityMapper.insertSelective(new UserAuthority(user.getUserId(),authorityId));
        }
        userMapper.insertSelective(user);
        return null;
    }
    @Override
    public User getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)userDetails;
        return user;
    }
}
