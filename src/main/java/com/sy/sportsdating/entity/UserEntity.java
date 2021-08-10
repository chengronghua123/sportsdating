package com.sy.sportsdating.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tz_user")
@Data
public class UserEntity {
    @TableId
    private int id;
    private String mobile;
    private String userName;
    private boolean sex;
    private Integer age;
    private Integer userType;
    private String headPic;
    private String password;
}
