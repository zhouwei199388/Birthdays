package com.zw.birthdays.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by lenovo on 2017/2/28.
 */
@Table(name = "UserBean")
public class UserBean extends Model{
    @Column(name = "userName")
    public String userName;
    @Column(name = "headUrl")
    public String headImgUrl;
    @Column(name = "birthdayTime")
    public long birthdayTime;
    @Column(name = "sex")//0:男  1：女
    public int sex;
}
