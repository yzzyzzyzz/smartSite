package com.ztjs.smartSite.dao;

import java.util.List;
import java.util.Map;

/**
 * ������
 * @author Administrator
 *
 */
public interface UtilsDao {
	/**
	 * ͨ��token��ѯ�û�Ȩ��
	 * 
	 * @param token
	 */
	public List<Map<String, Object>> getRuleByToken(String token);
}
