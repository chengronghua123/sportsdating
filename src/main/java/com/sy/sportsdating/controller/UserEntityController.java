package com.sy.sportsdating.controller;


import com.sy.sportsdating.bean.dto.TokenDto;
import com.sy.sportsdating.entity.UserEntity;
import com.sy.sportsdating.service.IUserEntityService;
import com.sy.sportsdating.util.JwtUtil;
import com.sy.sportsdating.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntityController {
    @Autowired
    private IUserEntityService userService;
    private JwtUtil jwtUtil;

    @GetMapping("/findAllUser")
    public List<UserEntity> findAllUser(){
        return userService.findAllUser();
    }

    /*
     * 电话号码获取用户信息
     * */
    @GetMapping("/findUserByMobile")
    public ResponseResult findUserByMobile(@RequestParam String mobile){
        return userService.findUserByMobile(mobile);
    }

    /*
     * 微信用户登录或者注册
     * */
    @PostMapping("/registerOrLogin")
    public ResponseResult registerOrLogin(@RequestParam(value = "mobile") String mobile,@RequestParam(value = "password") String password){
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        ResponseResult result = userService.findUserByMobile(mobile);
        if(result.getCode()==200){ // 登录
            UserEntity userEntity = (UserEntity) result.getData();
            if(userEntity.getPassword().equals(md5Password)||password.isEmpty()){ //密码登录 或者电话号码一键登录
                String token = jwtUtil.createToken(userEntity.getId()); //生成token
                TokenDto tokenDto = new TokenDto();
                tokenDto.setAccessToken(token);
                return ResponseResult.success(tokenDto);
            }else {
                return ResponseResult.error("密码错误");
            }
        }else { //注册
            UserEntity userEntity = new UserEntity();
            userEntity.setMobile(mobile);
            userEntity.setPassword(md5Password);
            userService.registerUser(userEntity);
            String token = jwtUtil.createToken(userEntity.getId());
            TokenDto tokenDto = new TokenDto();
            tokenDto.setAccessToken(token);
            return ResponseResult.success(tokenDto);
        }
    }

    /*
     * 更新用户信息
     * */
    @PostMapping
    public ResponseResult updateUserInfo(@Valid @RequestBody UserEntity userEntity){
        ResponseResult tokenRes =  jwtUtil.verity(); //验证token
        if(tokenRes.getCode()!=200){
            return tokenRes;
        }
        if(userEntity.getMobile().isEmpty()){
            return ResponseResult.error("电话号码不能为空");
        }
        ResponseResult result = userService.findUserByMobile(userEntity.getMobile());
        UserEntity beforUser = (UserEntity) result.getData();
        userEntity.setPassword(beforUser.getPassword());
        return userService.updateUserInfo(userEntity);
    }
}
