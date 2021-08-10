package com.sy.sportsdating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sportsdating.entity.UserEntity;
import com.sy.sportsdating.util.ResponseResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chengronghua
 * @since 2021-05-14
 */

public interface IUserEntityService extends IService<UserEntity> {

    /*
    * 获取所有用户
    */
    public List<UserEntity> findAllUser();

    /*
    * 通过电话号码获取用户
     */
    ResponseResult findUserByMobile(String mobile);

    /*
    * 注册用户
    * */
    ResponseResult registerUser(UserEntity userEntity);

    /*
    * 更新用户信息
    * */
    ResponseResult updateUserInfo(UserEntity userEntity);
}
