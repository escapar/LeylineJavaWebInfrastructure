package net.masadora.mall.business.infrastructure.common;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XUYI
 *	cookie工具类
 */
@Component
public class CookieUtil {


    private static MasadoraProps masadoraProps;

    @Autowired
    public void setMasadoraProps(MasadoraProps masadoraProps){
        CookieUtil.masadoraProps=masadoraProps;
    }

    /**
     * 删除普通cookie
     * @param cookieName
     * @param request
     * @param response
     */
	public static void removeUserKeyCookie(String cookieName, HttpServletRequest request, HttpServletResponse response){
		for (Cookie cookie : request.getCookies()) {
			if (cookieName.equals(cookie.getName())) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
	}
	
	/**
	 * 各子域删除主域cookie
	 * @param domain
	 * @param cookieName
	 * @param request
	 * @param response
	 */
	public static void removeUserKeyCookieByDomain(String domain, String cookieName, HttpServletRequest request, HttpServletResponse response){
		for (Cookie cookie : request.getCookies()) {
			if (cookieName.equals(cookie.getName())) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				cookie.setDomain(domain);
				response.addCookie(cookie);
			}
		}
	}

	/**
	 * 读取cookie
	 * @param request
	 * @return
	 */
	public static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}


    /**
     * 根据cookieName获得cookie
     * @param request
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request) {
        return getCookieByName(masadoraProps.cookieName,request);
    }

	/**
	 * 根据cookieName获得cookie
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static Cookie getCookieByName(String cookieName , HttpServletRequest request) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(cookieName)) {
			Cookie cookie = (Cookie) cookieMap.get(cookieName);
			return cookie;
		} else {
			return null;
		}
	}

	
	/**
	 * 增加普通cookie<br/>
	 * maxAge -1为浏览器周期 0为删除 正数为有效期 单位为秒
	 * @param cookieName
	 * @param cookieValue
	 * @param maxAge
	 * @param response
	 */
	public static void addCookie(String cookieName, String cookieValue, int maxAge, HttpServletResponse response){
		Cookie cookie = new Cookie(cookieName,cookieValue);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 各子域增加主域cookie
	 * @param domain
	 * @param cookieName
	 * @param cookieValue
	 * @param maxAge
	 * @param response
	 */
	public static void addCookieByDomain(String domain, String cookieName, String cookieValue, int maxAge, HttpServletResponse response){
		Cookie cookie = new Cookie(cookieName,cookieValue);
		cookie.setDomain(domain);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	


	
	/***************************************保存用户名cookie********************************************/
	
	/**
	 * 从cookie中获得用户名
	 * @param request
	 * @return
	 */
	public static String getUserNameByCookie(HttpServletRequest request){
		Cookie cookie = CookieUtil.getCookieByName(masadoraProps.saveCookieName, request);
		String username = null;
		if(cookie!=null){
			try{
				username = new String(Base64.decode(cookie.getValue()));
			}catch (Exception e) {
			}
		}
		return username;
	}
	
	/**
	 * 添加用户名cookie
	 * @param request
	 * @param response
	 * @param username
	 */
	public static void addUserNameCookie(HttpServletRequest request, HttpServletResponse response, String username){
		CookieUtil.addCookieByDomain(masadoraProps.domain, masadoraProps.saveCookieName, Base64.encode(username.getBytes()),masadoraProps.saveCookieAge,response);
	}
	
	/**
	 * 移除用户名cookie
	 * @param request
	 * @param response
	 */
	public static void removeUserNameCookie(HttpServletRequest request, HttpServletResponse response){
		CookieUtil.removeUserKeyCookieByDomain(masadoraProps.domain, masadoraProps.saveCookieName, request, response);
	}

    public static Boolean webKeyOKAY(String val){
        return val.equals(masadoraProps.cookieWebkey);
    }
	
	/***************************************登出cookie********************************************/
	/**
	 * 检查是否登出用户
	 * @param request
	 * @return
	 */
	public static Boolean checkLogOutByCookie(HttpServletRequest request){
		Cookie cookie = CookieUtil.getCookieByName(masadoraProps.logoutCookieName, request);
		return cookie != null ? true : false;
	}


}
