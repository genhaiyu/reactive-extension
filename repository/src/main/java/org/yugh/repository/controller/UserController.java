package org.yugh.repository.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.globalauth.common.enums.ResultEnum;
import org.yugh.globalauth.util.ResultJson;
import org.yugh.repository.entites.UserEntity;
import org.yugh.repository.service.IUserService;

import javax.validation.Valid;

/**
 * //
 *
 * @author: 余根海
 * @creation: 2019-07-10 14:41
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;


    @RequestMapping(value = "/getUserByName", method = RequestMethod.POST)
    public ResultJson<UserEntity> get(String userName) {
        try {
            UserEntity user = userService.getUserByUserName(userName);
            return ResultJson.ok(user);
        } catch (Exception e) {
            log.error("获取用户信息失败:{}, ", e.getMessage());
            return ResultJson.failure(ResultEnum.SERVER_ERROR, "获取用户信息失败");
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(@RequestBody @Valid UserEntity userDto) {
        try {
            userService.update(userDto);
            return ResultJson.ok("更新用户成功");
        } catch (Exception e) {
            log.error("更新用户失败:{}", e.getMessage());
            return ResultJson.failure(ResultEnum.SERVER_ERROR, "更新用户失败");
        }
    }


    /**
     * 同步用户信息数据
     *
     * @param userDto 用户信息
     * @return
     */
    @RequestMapping(value = "/syncUser", method = RequestMethod.POST)
    public ResultJson syncUser(@RequestBody @Valid UserEntity userDto) {
        try {
            boolean exist = userService.exists(userDto.getName());
            if (exist) {
                userService.update(userDto);
            } else {
                userService.addUser(userDto);
            }
            return ResultJson.ok("同步用户成功");
        } catch (Exception e) {
            log.error("同步用户失败:{}", e.getMessage());
            return ResultJson.failure(ResultEnum.SERVER_ERROR, "用户同步失败");
        }
    }
}
