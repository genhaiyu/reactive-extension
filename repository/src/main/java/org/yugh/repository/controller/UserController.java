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
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * // 项目引用了global-auth，里面包含了最大的exception，错误不需要在业务层抛
 * //新增mono语法
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


    /**
     * 新增mono语法
     */
    public Mono getUserInfo(String userName){
        return Mono.just(userService.getUserByUserName(userName));
    }





    @RequestMapping(value = "/getUserByName", method = RequestMethod.POST)
    public ResultJson<UserEntity> get(String userName) {
        return ResultJson.ok(userService.getUserByUserName(userName));
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(@RequestBody @Valid UserEntity userDto) {
        userService.update(userDto);
        return ResultJson.ok("更新用户成功");
    }


    /**
     * 同步用户信息数据
     *
     * @param userDto 用户信息
     * @return
     */
    @RequestMapping(value = "/syncUser", method = RequestMethod.POST)
    public ResultJson syncUser(@RequestBody @Valid UserEntity userDto) {
        boolean exist = userService.exists(userDto.getName());
        if (exist) {
            userService.update(userDto);
        } else {
            userService.addUser(userDto);
        }
        return ResultJson.ok("同步用户成功");
    }
}
