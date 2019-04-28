package com.temp.permission.entity;

import java.util.Date;
import java.util.List;

public class User {
    private Integer userId;
    private String userPhone;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userToken;
    private String userAvatar;
    private Integer userLoginCount;
    private String userLastIp;
    private Integer userStatus;
    private Date userCreateAt;
    private Date userUpdateAt;

    private List<Role> roleList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Integer getUserLoginCount() {
        return userLoginCount;
    }

    public void setUserLoginCount(Integer userLoginCount) {
        this.userLoginCount = userLoginCount;
    }

    public String getUserLastIp() {
        return userLastIp;
    }

    public void setUserLastIp(String userLastIp) {
        this.userLastIp = userLastIp;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Date getUserCreateAt() {
        return userCreateAt;
    }

    public void setUserCreateAt(Date userCreateAt) {
        this.userCreateAt = userCreateAt;
    }

    public Date getUserUpdateAt() {
        return userUpdateAt;
    }

    public void setUserUpdateAt(Date userUpdateAt) {
        this.userUpdateAt = userUpdateAt;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userToken='" + userToken + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userLoginCount=" + userLoginCount +
                ", userLastIp='" + userLastIp + '\'' +
                ", userStatus=" + userStatus +
                ", userCreateAt=" + userCreateAt +
                ", userUpdateAt=" + userUpdateAt +
                '}';
    }
}
