package com.ivp.xch.sys;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivp.xch.base.BaseEntity;


@Entity
@Table(name = "TUSER")
public class User extends BaseEntity {

    public User() {}

    public User(Long id, String userName, String uname, String upass) {
        this.id = id;
        this.userName = userName;
        this.uname = uname;
        this.password = upass;

    }

    /**
     * 用户姓名
     * 汉字
     */
    private String uname;

    /**
     * 用户登录时用的账号
     * 各种字符
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    // @Column(name = "mobile", length = 11, nullable = false)
    private String mobile;

    private String fax;

    private String tel;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


}
