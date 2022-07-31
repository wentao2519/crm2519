package com.waves.crm.settings.service;

import com.waves.crm.settings.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/5 23:22
 */


public interface UserService {

    /**
     * 根据用户名密码查询user
     * @param map
     * @return
     */
    User queryUserByLoginAvtAndPwd(Map<String, Object> map);

    List<User> queryAllUsers();

    int modifyLoginPwd(Map<String, Object> map);

    User queryUserByIdAndLoginPwd(Map<String, Object> map);

    int saveCreateUser(User user);

    User queryUserByLoginAct(String loginAct);

    User queryUserByName(String name);
}
