package com.baizhi.realm;

import com.baizhi.dao.AdminDAO;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.Admin;
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
    private AdminDAO adminDAO;

    @Override //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = new Admin();
        admin.setName(principal);
        Admin selectOne = adminDAO.selectOne(admin);
        SimpleAuthorizationInfo authorizationInfo = null;
        if (selectOne != null && selectOne.getRole().equals("svip")) {
            authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRole(selectOne.getRole());
            return authorizationInfo;
        }
        return null;
    }

    @Override  //  管理员  认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        Admin admin = new Admin();
        admin.setName(principal);
        Admin selectOne = adminDAO.selectOne(admin);
        if (selectOne != null) {
            AuthenticationInfo authenticationInfo =
                    new SimpleAuthenticationInfo(selectOne.getName(), selectOne.getPassword(),
                            ByteSource.Util.bytes(selectOne.getSalt()), this.getName());
            return authenticationInfo;
        }
        return null;
    }
}
