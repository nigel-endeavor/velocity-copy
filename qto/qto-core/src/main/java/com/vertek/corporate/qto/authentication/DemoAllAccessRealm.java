package com.vertek.corporate.qto.authentication;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Demo realm that grants all permissions to all users (including anonymous).
 * FOR DEVELOPMENT/DEMO USE ONLY.
 */
public class DemoAllAccessRealm extends AuthorizingRealm {

    public DemoAllAccessRealm() {
        // Accept any credentials — no real password checking in demo mode
        setCredentialsMatcher(new AllowAllCredentialsMatcher());
    }

    @Override
    public boolean supports(final AuthenticationToken token) {
        return true;
    }

    /**
     * Always grants all permissions regardless of principal.
     */
    @Override
    public boolean isPermitted(final PrincipalCollection principals, final String permission) {
        return true;
    }

    /**
     * Always grants all permissions regardless of principal.
     */
    @Override
    public boolean[] isPermitted(final PrincipalCollection principals, final String... permissions) {
        boolean[] results = new boolean[permissions.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = true;
        }
        return results;
    }

    /**
     * Always returns true indicating all permissions are granted.
     */
    @Override
    public boolean isPermittedAll(final PrincipalCollection principals, final Collection<org.apache.shiro.authz.Permission> permissions) {
        return true;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("*");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken)
            throws AuthenticationException {
        return new SimpleAuthenticationInfo("demo-user@demo.com", null, getName());
    }
}
