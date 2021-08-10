package com.sy.sportsdating.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sportsdating.entity.UserEntity;
import com.sy.sportsdating.mapper.UserEntityMapper;
import com.sy.sportsdating.service.IUserEntityService;
import com.sy.sportsdating.util.EmptyUtil;
import com.sy.sportsdating.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chengronghua
 * @since 2021-05-14
 */
@Service
public class UserEntityServiceImpl extends ServiceImpl<UserEntityMapper,UserEntity> implements IUserEntityService {
    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public List<UserEntity> findAllUser() {
        return userEntityMapper.findAllUser();
    }

    @Override
    public ResponseResult findUserByMobile(String mobile) {
        QueryWrapper<UserEntity> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("mobile",mobile);
        UserEntity userEntity=userEntityMapper.selectOne(userQueryWrapper);
        if(EmptyUtil.isEmpty(userEntity)){
            // 返回报错
            return ResponseResult.error("xxxx");

        }
        return ResponseResult.success(userEntity);
    }

    @Override
    public ResponseResult registerUser(UserEntity userEntity) {
        int flag =  userEntityMapper.insert(userEntity);
        if(flag==0){
            return  ResponseResult.error("新增用户信息失败");
        }
        return ResponseResult.success("新增用户信息成功");
    }

    @Override
    public ResponseResult updateUserInfo(UserEntity userEntity) {
        int flag = userEntityMapper.updateById(userEntity);
        if(flag==0){
            return  ResponseResult.error("更新用户信息失败");
        }
        return ResponseResult.success("更新用户信息成功");
    }
}
