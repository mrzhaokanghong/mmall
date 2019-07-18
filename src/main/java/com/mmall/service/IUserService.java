package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * 用户登录接口
 */
public interface IUserService {
    //登录
    ServerResponse<User> login(String username, String password);
    //注册
    ServerResponse<String> register(User user);
    //校验用户信息
    ServerResponse<String>checkValid(String str,String type);
    //密码找回提示问题
    ServerResponse selectQuestion(String username);
    //校验密码找回问题是否正确
    ServerResponse<String> checkAnswer(String username,String question,String answer);
    //用户密码找回,修改密码
    ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken);
    //登录状态用户修改密码
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);
    //更新个人信息接口
    ServerResponse<User> updateInformation(User user);
    //查询用户个人信息
    ServerResponse<User>getInformation(Integer userId);
    //校验用户是否是管理员
    ServerResponse checkAdminRole(User user);
}
