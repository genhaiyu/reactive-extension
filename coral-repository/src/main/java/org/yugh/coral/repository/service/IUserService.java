package org.yugh.coral.repository.service;


import org.yugh.coral.repository.entites.UserEntity;
import org.yugh.coral.repository.pojo.dto.UserDTO;

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
    UserEntity getUserByUserName(String userName);

    /**
     * 新增用户
     *
     * @param userDTO
     * @return
     */
    void addUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param userDTO
     */
    void update(UserDTO userDTO);


    /**
     * 查询用户是否存在
     *
     * @param userName
     * @return
     */
    boolean exists(String userName);
}
