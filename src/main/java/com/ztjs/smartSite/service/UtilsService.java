package com.ztjs.smartSite.service;

import java.util.List;
import java.util.Map;

/**
 * 工具类
 * @author Administrator
 *
 */
public interface UtilsService{
	/**
	 * 通过token查询用户权限
	 * 
	 * @param token
	 */
	public List<Map<String, Object>> getRuleByToken(String token);
}
