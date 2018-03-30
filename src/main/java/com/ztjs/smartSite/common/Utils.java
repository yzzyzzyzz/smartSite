package com.ztjs.smartSite.common;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ztjs.smartSite.service.UtilsService;

/**
 * 工具类
 * @author Administrator
 *
 */
@Controller
public class Utils {
	/**
	 * 通过token查询用户权限
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
