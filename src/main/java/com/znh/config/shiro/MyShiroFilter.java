package com.znh.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class MyShiroFilter {

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		/*Map<String, Filter> map = new LinkedHashMap<String, Filter>();
		map.put("authc", getFormAuthenticationFilter());*/
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会拦截的URL
		filterChainDefinitionMap.put("/static/**", "anon");				// 静态资源
		filterChainDefinitionMap.put("/loginForm", "anon");			// 登录请求
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// 配置在最后面
		filterChainDefinitionMap.put("/", "authc");
		filterChainDefinitionMap.put("/**", "authc");
		// 登录的URL接口（Shiro可以进行识别）
		shiroFilterFactoryBean.setLoginUrl("/");
		
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 这个map中包含了上面自定义的信息，配置到setFilter中
		//shiroFilterFactoryBean.setFilters(map);
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;

	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于
														// md5(md5(""));
		return hashedCredentialsMatcher;
	}
	
	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
		mappings.setProperty("UnauthorizedException","403");
		r.setExceptionMappings(mappings);  // None by default
		r.setDefaultErrorView("403");    // No default
		r.setExceptionAttribute("ex");     // Default is "exception"
		r.setWarnLogCategory("example.MvcLogger");     // No default
		return r;
	}
	
	/**
	 * EnterpriseCacheSessionDAO shiro sessionDao层的实现；
	 * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
	 */
	@Bean
	public EnterpriseCacheSessionDAO enterCacheSessionDAO() {
		EnterpriseCacheSessionDAO enterCacheSessionDAO = new EnterpriseCacheSessionDAO();
		// 添加缓存管理器
		// enterCacheSessionDAO.setCacheManager(ehCacheManager());
		// 添加ehcache活跃缓存名称（必须和ehcache缓存名称一致）
		enterCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		return enterCacheSessionDAO;
	}
	
	/**
	 *
	 * @描述：sessionManager添加session缓存操作DAO
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// sessionManager.setCacheManager(ehCacheManager());
		sessionManager.setSessionDAO(enterCacheSessionDAO());
		return sessionManager;
	}
	
	/**
	 * 自定义realm
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}
	
	@Bean
	public DefaultWebSecurityManager  securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(myShiroRealm());
		// //注入session管理器;
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}
}
