package com.ztjs.smartSite.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztjs.smartSite.dao.IUserDao;
import com.ztjs.smartSite.pojo.User;
import com.ztjs.smartSite.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}

}
