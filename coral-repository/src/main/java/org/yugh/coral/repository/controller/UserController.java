package org.yugh.coral.repository.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.yugh.coral.core.result.ResultJson;
import org.yugh.coral.repository.entites.UserEntity;
import org.yugh.coral.repository.pojo.dto.UserDTO;
import org.yugh.coral.repository.service.IUserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
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

    @Autowired(required = false)
    WebClient webClient;


    @GetMapping("/hooks-debug")
    public Mono webClient() {
        Hooks.onOperatorDebug();
        return Mono.just("Hooks.onOperatorDebug!!");
    }


    @GetMapping("/flux")
    public Flux helloFlux(String flux) {
        return Flux.just("hello " + flux);
    }

    @GetMapping("/mono")
    public Mono helloMono(String mono) {
        return Mono.just("hello " + mono);
    }


    @RequestMapping(value = "/getUserByName", method = RequestMethod.POST)
    public ResultJson<UserEntity> get(String userName) {
        return ResultJson.ok(userService.getUserByUserName(userName));
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(@RequestBody @Valid UserDTO userDto) {
        userService.update(userDto);
        return ResultJson.ok("更新用户成功");
    }


    /**
     * 同步用户信息数据
     *
     * @param userDTO 用户信息
     * @return
     */
    @RequestMapping(value = "/syncUser", method = RequestMethod.POST)
    public ResultJson syncUser(@RequestBody @Valid UserDTO userDTO) {
        boolean exist = userService.exists(userDTO.getName());
        if (exist) {
            userService.update(userDTO);
        } else {
            userService.addUser(userDTO);
        }
        return ResultJson.ok("同步用户成功");
    }
}
