package com.hqj.web.user.controller;

import com.alibaba.fastjson.JSON;
import com.hqj.common.dto.ResponseDTO;
import com.hqj.user.dto.UserDTO;
import com.hqj.user.dto.UserQueryDTO;
import com.hqj.user.service.UserService;
import com.hqj.common.constant.Constant;
import com.hqj.common.controller.BaseController;
import com.hqj.common.dto.PageDTO;
import com.hqj.common.enums.YesNo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(value = "用户管理器", tags = "User.Manager")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @ApiOperation("查询用户")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseDTO<PageDTO> listIndex(UserQueryDTO reqDTO) {
        if(reqDTO.getPageNum() == null) {
            reqDTO.setPageNum(Constant.PAGE_NUM);
        }
        if(reqDTO.getPageSize() == null) {
            reqDTO.setPageSize(Constant.PAGE_SIZE);
        }
        UserDTO user = getUser();
        if(user.getManaged() != null && YesNo.YES.getValue() != user.getManaged()) {
            reqDTO.setUserId(user.getId());
        }
        PageDTO page = userService.list(reqDTO);
        return ResponseDTO.success(page);
    }

    @ApiOperation("插入/更新用户")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public ResponseDTO<Long> saveOrUpdate(@RequestBody UserDTO userDTO) {
        Long userId = getUserId();
        userDTO.setUserId(userId);
        Long id = userService.insertOrUpdateUser(userDTO);
        return ResponseDTO.success(id);
    }

    @ApiOperation("管理员")
    @RequestMapping(value = "/managed", method = RequestMethod.POST)
    public ResponseDTO<Boolean> managed(@RequestBody UserDTO userDTO) {
        userService.managed(userDTO);
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseDTO<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("登陆校验")
    @RequestMapping(value = "/login/check", method = RequestMethod.POST)
    public ResponseDTO<Boolean> check(HttpServletResponse response, @RequestBody UserDTO userDTO) {
        UserDTO user = userService.check(userDTO);
        if(user != null) {
            String token = UUID.randomUUID().toString();
            // 设置token
            setInRedis(Constant.USER_PREFIX + token, JSON.toJSONString(user), 1L, TimeUnit.DAYS);
            // 设置cookie
            Cookie cookie = new Cookie(Constant.ES_SESSION_ID, token);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60 * 1000);
            response.addCookie(cookie);
        }
        return ResponseDTO.success(Boolean.TRUE);
    }

    @ApiOperation("获取当前用户")
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public ResponseDTO<UserDTO> getCurrentUser() {
        UserDTO user = getUser();
        return ResponseDTO.success(user);
    }
}
