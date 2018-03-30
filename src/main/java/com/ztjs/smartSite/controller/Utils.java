package com.ztjs.smartSite.controller;

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
	public void getRuleByToken(String token) {

	}
}
