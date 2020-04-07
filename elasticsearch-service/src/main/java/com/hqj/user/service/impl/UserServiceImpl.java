package com.hqj.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqj.user.bean.UserQueryBean;
import com.hqj.user.domain.UserDomain;
import com.hqj.user.dto.UserDTO;
import com.hqj.user.dto.UserQueryDTO;
import com.hqj.utils.BeanUtils;
import com.hqj.common.dto.PageDTO;
import com.hqj.common.enums.YesNo;
import com.hqj.common.exception.ServiceException;
import com.hqj.utils.MD5Utils;
import com.hqj.user.mapper.UserMapper;
import com.hqj.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_NAME_EXISTS = "用户名已存在";
    private static final String USER_NOT_EXISTS = "用户不存在";
    private static final String USER_PASSWORD_ILLEGAL = "用户名/密码不正确";

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户
     *
     * @param queryDTO
     * @return
     */
    @Override
    public PageDTO<UserDTO> list(UserQueryDTO queryDTO) {
        UserQueryBean query = BeanUtils.map(queryDTO, UserQueryBean.class);
        // 分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserDomain> users = userMapper.selectByQuery(query);
        PageInfo<UserDomain> pageInfo = new PageInfo(users);
        // 转换为PageDTO
        PageDTO<UserDTO> page = BeanUtils.map(pageInfo, PageDTO.class);
        page.setList(BeanUtils.mapList(pageInfo.getList(), UserDTO.class));
        return page;
    }

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    @Override
    public UserDTO getUser(Long id) {
        if(id == null || id == 0L) {
            return null;
        }
        UserDomain user = userMapper.selectById(id);
        if(user != null) {
            return BeanUtils.map(user, UserDTO.class);
        }
        return null;
    }

    /**
     * 插入/更新用户
     *
     * @param user
     * @return
     */
    @Override
    public Long insertOrUpdateUser(UserDTO user) {
        if(user != null) {
            UserDomain user1 = userMapper.selecByUserName(user.getUserName());
            if(user1 != null) {
                throw new ServiceException(USER_NAME_EXISTS);
            }
            UserDomain user2 = BeanUtils.map(user, UserDomain.class);
            String password = MD5Utils.md5(user2.getPassword());
            user2.setPassword(password);
            user2.setCreateBy(user.getUserId());
            user2.setUpdateBy(user.getUserId());
            userMapper.insertOrUpdate (user2);
            return user2.getId();
        }
        return null;
    }

    /**
     * 管理员
     *
     * @param user
     */
    @Override
    public void managed(UserDTO user) {
        if(user.getManaged() == null) {
            user.setManaged(YesNo.NO.getValue());
        }
        userMapper.managed(BeanUtils.map(user, UserDomain.class));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    /**
     * 登陆校验
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO check(UserDTO userDTO) {
        UserDomain user = userMapper.selecByUserName(userDTO.getUserName());
        if(user == null) {
            throw new ServiceException(USER_NOT_EXISTS);
        }
        String password = MD5Utils.md5(userDTO.getPassword());
        if(!password.equals(user.getPassword())) {
            throw new ServiceException(USER_PASSWORD_ILLEGAL);
        }
        return BeanUtils.map(user, UserDTO.class);
    }
}
