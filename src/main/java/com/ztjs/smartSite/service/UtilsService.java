package com.ztjs.smartSite.service;

import java.util.List;
import java.util.Map;

/**
 * ������
 * @author Administrator
 *
 */
public interface UtilsService{
	/**
	 * ͨ��token��ѯ�û�Ȩ��
	 * 
	 * @param token
	 */
	public List<Map<String, Object>> getRuleByToken(String token);
}
