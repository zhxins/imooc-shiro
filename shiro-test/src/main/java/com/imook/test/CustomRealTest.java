package com.imook.test;

import com.imoock.shiro.realm.CustomReal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


/**
 * @author zhaoxin
 * @create 2019-07-22 9:52
 * @desc
 **/
public class CustomRealTest {

    @Test
    public void testAuthentication() {

        CustomReal customReal = new CustomReal();
        // 1、构建SerruityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customReal);

        // shiro加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customReal.setCredentialsMatcher(matcher);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        // 可分别验证用户名或密码不对的情况
        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");

        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.isAuthenticated();

        subject.checkRole("user");

        subject.checkPermission("user:add");

    }
}
