package com.ztjs.smartSite.dao;

import java.util.List;
import java.util.Map;

/**
 * 工具类
 * @author Administrator
 *
 */
public interface UtilsDao {
	/**
	 * 通过token查询用户权限
	 * 
	 * @param token
	 */
	public List<Map<String, Object>> getRuleByToken(String token);
}
