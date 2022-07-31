package com.waves.crm.settings.service.impl;

import com.waves.crm.settings.domain.User;
import com.waves.crm.settings.mapper.UserMapper;
import com.waves.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/5 23:28
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByLoginAvtAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndPwd(map);
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int modifyLoginPwd(Map<String, Object> map) {
        return userMapper.updateLoginPwdById(map);
    }

    @Override
    public User queryUserByIdAndLoginPwd(Map<String, Object> map) {
        return userMapper.selectUserByIdAndLoginPwd(map);
    }

    @Override
    public int saveCreateUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User queryUserByLoginAct(String loginAct) {
        return userMapper.selectUserByLoginAct(loginAct);
    }

    @Override
    public User queryUserByName(String name) {
        return userMapper.selectUserByName(name);
    }
}
