package com.rightdarkdoc.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class User {

    private Integer userid;
    private String username;
    private String password;
    private String phone;
    private Date birthday;
    private String email;
    private String avatar;
    private String description;

    /**
     * 注册时用到的构造函数
     * @param userName
     * @param password
     * @param phone
     * @param email
     */
    public User(String userName, String password, String phone, String email) {
        this.username = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //
//    @Override
//    public String toString() {
//        return "User{" +
//                "userId=" + userId +
//                ", userName='" + userName + '\'' +
//                ", passWord='" + passWord + '\'' +
//                ", phone='" + phone + '\'' +
//                ", birthday=" + birthday +
//                ", email='" + email + '\'' +
//                ", avatar='" + avatar + '\'' +
//                ", description='" + description + '\'' +
//                '}';
//    }
}
