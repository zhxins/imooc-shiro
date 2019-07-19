package com.imook.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author zhaoxin
 * @create 2019-07-19 16:43
 * @desc
 **/
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("root");


    }

    @Test
    public void testAuthentication() {

        JdbcRealm jdbcRealm = new JdbcRealm();

        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql = "SELECT `password` from t_user t where t.`name`= ?";
        jdbcRealm.setAuthenticationQuery(sql);



        // 1、构建SerruityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        // 可分别验证用户名或密码不对的情况
        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");

        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.isAuthenticated();

        subject.checkRole("admin");

        subject.checkRoles("admin", "user");

        subject.checkPermission("user:select");


    }

}
