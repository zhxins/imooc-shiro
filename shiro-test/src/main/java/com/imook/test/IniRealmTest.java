package com.imook.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import java.security.Security;

/**
 * @author zhaoxin
 * @create 2019-07-19 16:36
 * @desc
 **/
public class IniRealmTest {

    @Test
    public void testAuthentication() {

        IniRealm iniRealm = new IniRealm("classpath:user.ini");
        // 1、构建SerruityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        // 可分别验证用户名或密码不对的情况
        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");

        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.isAuthenticated();

        subject.checkRole("admin");

        subject.checkPermission("user:add");


    }


}
