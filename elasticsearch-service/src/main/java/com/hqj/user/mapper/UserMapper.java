package com.hqj.user.mapper;

import com.hqj.user.bean.UserQueryBean;
import com.hqj.user.domain.UserDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户mapper
 */
public interface UserMapper {
    /**
     * 查询索引
     *
     * @param query
     * @return
     */
    List<UserDomain> selectByQuery(UserQueryBean query);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    UserDomain selectById(@Param("id") Long id);

    /**
     * 通过用户名查询
     *
     * @param userName
     * @return
     */
    UserDomain selecByUserName(@Param("userName") String userName);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 插入
     *
     * @param user
     * @return
     */
    Long insert(UserDomain user);

    /**
     * 更新
     *
     * @param user
     */
    void update(UserDomain user);

    /**
     * 插入或更新
     *
     * @param user
     * @return
     */
    Long insertOrUpdate(UserDomain user);

    /**
     * 管理员
     *
     * @param user
     */
    void managed(UserDomain user);
}
