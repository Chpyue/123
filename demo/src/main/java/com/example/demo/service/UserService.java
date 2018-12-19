package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

/**
 * @ProjectName: demo
 * @Package: com.example.demo.service
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: yueChunPeng
 * @CreateDate: 2018-12-19 13:32
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018-12-19 13:32
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public interface UserService {
    List<User> getUserList();
}
