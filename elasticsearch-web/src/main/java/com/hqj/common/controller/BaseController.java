package com.hqj.common.controller;

import com.alibaba.fastjson.JSON;
import com.hqj.user.dto.UserDTO;
import com.hqj.utils.TokenUitls;
import com.hqj.common.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取当前用户
     *
     * @return
     */
    public UserDTO getUser() {
        String token = TokenUitls.getToken(request);
        if(StringUtils.isEmpty(token)) {
            return new UserDTO();
        }
        String userStr = redisTemplate.opsForValue().get(Constant.USER_PREFIX + token);
        return JSON.parseObject(userStr, UserDTO.class);
    }

    /**
     * 获取当前用户Id
     *
     * @return
     */
    public Long getUserId() {
        UserDTO user = getUser();
        if(user == null) {
            return null;
        }
        return user.getId();
    }

    /**
     * 设置redis
     *
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    public void setInRedis(String key, String value, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }
}
