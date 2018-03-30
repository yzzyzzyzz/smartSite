package com.ztjs.smartSite.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztjs.smartSite.dao.UtilsDao;
import com.ztjs.smartSite.service.UtilsService;


/**
 * ������
 * @author Administrator
 *
 */
@Service("UtilsService")
public class UtilsServiceImpl implements UtilsService {
	/**
	 * ͨ��token��ѯ�û�Ȩ��
	 * 
	 * @param token
	 */
	@Resource
	private UtilsDao utilsDao;
	public List<Map<String, Object>> getRuleByToken(String token) {
		return utilsDao.getRuleByToken(token);
	};
}
