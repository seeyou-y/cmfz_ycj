package com.baizhi.conf;


import com.baizhi.realm.MyRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroFilterConf {
    /**
     * 将shirofilter工厂交给spring工厂
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置过滤器
        //过滤器链的集合
        // AnonymousFilter              匿名过滤器  anon
        //FormAuthenticationFilter    认证过滤器   authc
        HashMap<String, String> map = new HashMap<>();
        map.put("/admin/*","anon");
        map.put("/login/**","anon");
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/login/login.jsp");

        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器
     *
     * @param realm
     * @param CacheManager
     * @return
     */

    @Bean
    public SecurityManager getSecurityManager(Realm realm, CacheManager CacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(CacheManager);
        return securityManager;
    }

    /**
     * 自定义realm
     *
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public Realm getRealm(CredentialsMatcher credentialsMatcher) {
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    /**
     * 自定义凭证匹配器
     * 使用 md5 加密
     * 散列 1024 次
     *
     * @return
     */
    @Bean
    public CredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
    }

    /**
     * 自定义缓存   开启缓存
     * 优化授权访问数据库次数
     *
     * @return
     */
    @Bean
    public CacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }
}
