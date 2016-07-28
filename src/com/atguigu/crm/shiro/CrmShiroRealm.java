package com.atguigu.crm.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Navigation;
import com.atguigu.crm.service.UserService;

@Component
public class CrmShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		System.out.println("---------------------------------------");
		
		User user = (User) principals.getPrimaryPrincipal();
		
		List<Authority> authorities = user.getRole().getAuthorities();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		for (Authority authority : authorities) {
			System.out.println(authority.getName());
			info.addRole(authority.getName());
		}
		
		return info;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String username = upToken.getUsername();

		User user = userService.getByUserName(username);

		if (user == null) {
			throw new UnknownAccountException("该用户不存在.");
		}

		if (user.getEnabled() != 1) {
			throw new LockedAccountException("该用户被锁定.");
		}

		Object principal = user;

		Object hashedCredentials = user.getPassword();

		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());

		String realmName = getName();

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt,
				realmName);

		return info;
	}

	// @PostConstruct 相当于配置文件中的 init-method
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		setCredentialsMatcher(credentialsMatcher);
	}

	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		String credentials = "123456";
		ByteSource salt = ByteSource.Util.bytes("e2b87e6eced06509");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);

		System.out.println(result);
	}
}
