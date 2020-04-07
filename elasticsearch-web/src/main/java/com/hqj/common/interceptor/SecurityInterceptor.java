package com.hqj.common.interceptor;

import com.hqj.utils.TokenUitls;
import com.hqj.common.constant.Constant;
import com.hqj.common.exception.SecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = TokenUitls.getToken(httpServletRequest);
        if(StringUtils.isEmpty(token)) {
            if(httpServletRequest.getRequestURI().startsWith("/page") || httpServletRequest.getRequestURI().equals("/")) {
                httpServletResponse.sendRedirect("/page/login.html");
            } else {
                throw new SecurityException(Constant.TOKEN_INVALID);
            }
            return false;
        }
        String user = redisTemplate.opsForValue().get(Constant.USER_PREFIX + token);
        if(StringUtils.isEmpty(user)) {
            if(httpServletRequest.getRequestURI().startsWith("/page") || httpServletRequest.getRequestURI().equals("/")) {
                httpServletResponse.sendRedirect("/page/login.html");
            } else {
                throw new SecurityException(Constant.TOKEN_INVALID);
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
