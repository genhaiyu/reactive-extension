package org.yugh.repository.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yugh.common.model.User;
import org.yugh.common.pojo.dto.UserDTO;
import org.yugh.common.util.JsonResult;
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
    public JsonResult<User> get(String userName) {
        try {
            User user = userService.getUserByUserName(userName);
            return JsonResult.buildSuccessResult(user);
        } catch (Exception e) {
            log.error("获取用户信息失败:{}, ", e.getMessage());
            return JsonResult.buildErrorResult(e.getMessage(), "获取用户信息失败");
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public JsonResult addUser(@RequestBody @Valid UserDTO userDto) {
        try {
            User userDo = new User();
            BeanUtils.copyProperties(userDto, userDo);
            userService.addUser(userDo);
            return JsonResult.buildSuccessResult("添加用户成功");
        } catch (Exception e) {
            log.error("添加用户失败:{}", e.getMessage());
            return JsonResult.buildErrorResult(e.getMessage(), "添加用户失败");
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult update(@RequestBody @Valid UserDTO userDto) {
        try {
            User userDo = new User();
            BeanUtils.copyProperties(userDto, userDo);
            userService.update(userDo);
            return JsonResult.buildSuccessResult("更新用户成功");
        } catch (Exception e) {
            log.error("更新用户失败:{}", e.getMessage());
            return JsonResult.buildErrorResult(e.getMessage(), "更新用户失败");
        }
    }


    /**
     * 同步用户信息数据
     *
     * @param userDto 用户信息
     * @return
     */
    @RequestMapping(value = "/syncUser", method = RequestMethod.POST)
    public JsonResult syncUser(@RequestBody @Valid UserDTO userDto) {
        try {
            User userDo = new User();
            BeanUtils.copyProperties(userDto, userDo);
            boolean exist = userService.exists(userDo.getUserName());
            if (exist) {
                userService.update(userDo);
            } else {
                userService.addUser(userDo);
            }
            return JsonResult.buildSuccessResult("同步用户成功");
        } catch (Exception e) {
            log.error("同步用户失败:{}", e.getMessage());
            return JsonResult.buildErrorResult(e.getMessage(), "用户同步失败");
        }
    }
}
