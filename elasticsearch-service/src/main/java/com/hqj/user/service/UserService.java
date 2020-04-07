package com.hqj.user.service;

import com.hqj.user.dto.UserDTO;
import com.hqj.user.dto.UserQueryDTO;
import com.hqj.common.dto.PageDTO;

/**
 * 用户服务
 */
public interface UserService {
    /**
     * 查询用户
     *
     * @param queryDTO
     * @return
     */
    PageDTO<UserDTO> list(UserQueryDTO queryDTO);

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    UserDTO getUser(Long id);

    /**
     * 插入/更新用户
     *
     * @param user
     * @return
     */
    Long insertOrUpdateUser(UserDTO user);

    /**
     * 管理员
     *
     * @param user
     */
    void managed(UserDTO user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    void deleteUser(Long id);

    /**
     * 登陆校验
     *
     * @param userDTO
     * @return
     */
    UserDTO check(UserDTO userDTO);
}
