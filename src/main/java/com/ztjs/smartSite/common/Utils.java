package com.ztjs.smartSite.common;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ztjs.smartSite.service.UtilsService;

/**
 * ������
 * @author Administrator
 *
 */
@Controller
public class Utils {
	/**
	 * ͨ��token��ѯ�û�Ȩ��
	 * 
	 * @param token
	 */
	@Autowired
	private UtilsService utilsService;
	public List<Map<String, Object>> getRuleByToken(String token) {
		List<Map<String, Object>> a=utilsService.getRuleByToken("1");
		return a;

	}
}
