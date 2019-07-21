package org.yugh.repository.service;


import org.yugh.common.model.User;

/**
 * //用户服务层
 *
 * @author: 余根海
 * @creation: 2019-07-10 14:15
 * @Copyright © 2019 yugenhai. All rights reserved.
 */

public interface IUserService {

    /**
     * 查询用户
     *
     * @param userName
     * @return
     */
    User getUserByUserName(String userName);

    /**
     * 新增用户
     *
     * @param userDo
     * @return
     */
    void addUser(User userDo);

    /**
     * 更新用户
     *
     * @param userDo
     */
    void update(User userDo);


    /**
     * 查询用户是否存在
     *
     * @param userName
     * @return
     */
    boolean exists(String userName);
}
