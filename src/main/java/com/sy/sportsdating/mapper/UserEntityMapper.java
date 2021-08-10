package com.sy.sportsdating.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sportsdating.entity.UserEntity;

import java.util.List;

/**
 * <p>
 *  UserEntity持久层
 * </p>
 *
 * @author chengronghua
 * @since 2021-05-14
 */
public interface UserEntityMapper extends BaseMapper<UserEntity> {
    /*
    * 获取所有用户列表
    * */
    public List<UserEntity> findAllUser();

}
