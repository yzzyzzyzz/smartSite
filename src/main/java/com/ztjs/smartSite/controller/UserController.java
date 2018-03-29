package com.ztjs.smartSite.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztjs.smartSite.pojo.User;
import com.ztjs.smartSite.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/showUser")
	@ResponseBody
	public JSONArray toIndex(HttpServletRequest request){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		JSONObject a=new JSONObject();
		JSONObject c=new JSONObject();
		JSONArray b=new JSONArray();
		a.put("1", 2);
		c.put("2", 1);
		b.add(a);
		b.add(c);
		return b;
	}
}
