package net.masadora.mall.business.infrastructure.common;


import net.masadora.mall.business.domain.user.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XUYI
 * 提供取当前User对象的方法；提供本系统JAVA内存变量的增删改查操作
 */
public class ApplicationUtil {
	//本地线程
	private static ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();
	//内存环境变量
	private static Map<String, Object> environmentVariable = new HashMap<String, Object>();
	//清除一级缓存本地线程
	private static final ThreadLocal<Boolean> hibernateCacheClearThreadLocal = new ThreadLocal<Boolean>();
	
	public static void setHibernateCacheClearThreadLocal(Boolean flag){
		hibernateCacheClearThreadLocal.set(flag);
	}
	
	public static Boolean getHibernateCacheClearThreadLocal(){
		return hibernateCacheClearThreadLocal.get();
	}
	
	public static void removeHibernateCacheClearThreadLocal(){
		hibernateCacheClearThreadLocal.remove();
	}
	
	public static void setCurrentUser(User user){
		userThreadLocal.set(user);
	}
	
	public static User getCurrentUser(){
		return userThreadLocal.get();
	}
	
	public static void removeCurrentUser(){
		userThreadLocal.remove();
	}
	
	/**
	 * 启动时加载所需内存环境变量方法
	 * @param <T>
	 * @param variableName
	 * @param object
	 */
	public synchronized static <T extends Object> void putMemoryVariable(String variableName, T object){
		environmentVariable.put(variableName, object);
	}  
	
	/**
	 * 根据内存环境变量名找到该变量value,可能是任意类型
	 * @param <T>
	 * @param variableName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized static <T extends Object> T getMemoryVariable(String variableName){
		return (T)environmentVariable.get(variableName);
	}
	
	/**
	 * 根据内存环境变量名删除该变量
	 * @param <T>
	 * @param variableName
	 * @return
	 */
	public synchronized static void deleteMemoryVariable(String variableName){
		environmentVariable.remove(variableName);
	}
	
}
