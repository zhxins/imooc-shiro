package com.imoock.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author zhaoxin
 * @create 2019-07-22 9:03
 * @desc
 **/
public class CustomReal extends AuthorizingRealm{

    Map<String, String> userMap = new HashMap<String, String>();
    {

//        userMap.put("Mark", "123456");

        // 123456 经过md5加密之后的密文
        userMap.put("Mark", "e10adc3949ba59abbe56e057f20f883e");

        // 加盐后的密文，盐为Mark
        userMap.put("Mark", "283538989cef48f3d7d8a1c1bdf2008f");
        super.setName("customReal");

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String)principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(userName);

        Set<String> permissions = getPermissionByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);


        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        sets.add("user:update");
        return sets;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 从主体传过来的认证信息中，获得用户名
        String userName = (String)authenticationToken.getPrincipal();

        // 通过用户名到数据库中获得凭证
        String password = getPasswordByUserName(userName);

        if (null == password) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("Mark", password, "customReal");

        // 如果加盐，则需要设置加盐的名称
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));


        return authenticationInfo;
    }

    /**
     * 模拟数据库查询凭证
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }

    private  Set<String> getRolesByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    public static void main(String[] args) {

        // 加盐
        Md5Hash md5Hash = new Md5Hash("123456", "Mark");
        System.out.println(md5Hash);
    }
}
