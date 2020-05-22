package com.znh.config.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.znh.model.User;
import com.znh.service.system.UserService;

public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
    private SessionDAO sessionDAO;

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO 自动生成的方法存根
		String username = (String) token.getPrincipal();
		User user = userService.selectUserByUserName(username);
		if(user==null) {
			return null;
		}
		 // 通过sessionDAO里缓存的登录信息解决重复登录的问题
		User userSession = null;
		 // 获取用户的输入的密码
        String password = new String((char[])token.getCredentials());
        // 将输入的密码加密然后与sessionDAO里所存之前登录成功的用户密码进行比较
        String salt = user.getSalt();
        // 随机散列两次
        int hashIterations = 2;
    	Md5Hash hash = new Md5Hash(password,salt,hashIterations);
    	//获取在线的session            
        Collection<Session> sessionCollection = sessionDAO.getActiveSessions();
        for (Session session : sessionCollection){
        	//session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY); 获取simpleAuthenticationInfo的第一个参数的值
        	if(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) != null) {
        		//根据session build出一个subject
        		Subject subject = new Subject.Builder().session(session).buildSubject();
        		//拿到这个登陆的对象
        		userSession = (User) subject.getPrincipal();
        		//判断ID能否匹配        
        		if (user.getUserId().equals(userSession.getUserId())) {
        			//判断密码是否一致
        			if(userSession.getPassword().equals(hash.toString())){
        				//两者一致的时候，设置这个session的失效时间 （0：立刻）
            			session.setTimeout(0);
            			break;
        			}
        		}
        	}
        }
        // shiro框架中存储在session中的认证信息过期时间默认30分钟，将过期时间与配置文件设置的账号闲置时间同步。
        // 通过dom4j读取ehcache-shiro.xml文件拿到设置的闲置时间
        /*SAXReader reader = new SAXReader();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ehcacheShiro.xml");
        int sessionExpiration = 0;
        Document document;
		try {			
			document = reader.read(inputStream);
			Element root = document.getRootElement();
	        List<Element> childElements = root.elements();
	        for (Element child : childElements) {
	            List<Attribute> attributeList = child.attributes();
	            for (Attribute attr : attributeList) {
	                if (attr.getName().equals("timeToLiveSeconds")) {
	                	sessionExpiration = Integer.parseInt(attr.getValue())*1000;
					}
	            }
	        }
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 通过setTimeout方法设置session认证信息过期时间（单位：ms）
        SecurityUtils.getSubject().getSession().setTimeout(sessionExpiration);*/
        // 登录验证
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, // 用户对象--数据库
				user.getPassword(), // 密码--数据库
				ByteSource.Util.bytes(user.getSalt()), getName() // realm name
		);
		return simpleAuthenticationInfo;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

}
