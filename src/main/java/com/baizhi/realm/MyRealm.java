package com.baizhi.realm;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserDAO userDAO;

    @Override //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        User user = new User();
        user.setUsername(principal);
        User selectOne = userDAO.selectOne(user);
        SimpleAuthorizationInfo authorizationInfo = null;
    /*    if (selectOne != null && selectOne.getRole().equals("user")) {
            authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRole(selectOne.getRole());
            authorizationInfo.addStringPermission("user:query");
            return authorizationInfo;
        }
        if (selectOne != null && selectOne.getRole().equals("vip")) {
            authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRole(selectOne.getRole());
            authorizationInfo.addStringPermission("user:add");
            authorizationInfo.addStringPermission("user:update");
            authorizationInfo.addStringPermission("user:query");
            return authorizationInfo;
        }*/
        return null;
    }

    @Override  //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        User user = new User();
        user.setUsername(principal);
        User selectOne = userDAO.selectOne(user);
        if (selectOne != null) {
            AuthenticationInfo authenticationInfo =
                    new SimpleAuthenticationInfo(selectOne.getUsername(), selectOne.getPassword(),
                            ByteSource.Util.bytes(selectOne.getSalt()), this.getName());
            return authenticationInfo;
        }
        return null;
    }
}
