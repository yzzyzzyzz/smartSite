package com.ztjs.smartSite.controller;

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
	public void getRuleByToken(String token) {

	}
}
