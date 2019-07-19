package com.imook.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhaoxin
 * shiro 认证
 * @create 2019-07-17 17:52
 * @desc
 **/
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("user","123456","admin");
    }


    @Test
    public void testAuthentication() {

        // 1、构建SerruityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject =  SecurityUtils.getSubject();

        // 可分别验证用户名或密码不对的情况
        UsernamePasswordToken token = new UsernamePasswordToken("user","123456");

        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.isAuthenticated();

//        subject.logout();
//        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        // 检查当前主体是否存在参数的角色
        subject.checkRole("admin");














    }
}
