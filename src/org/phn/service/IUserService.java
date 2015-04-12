package org.phn.service;

import java.util.Map;

import org.phn.bean.User;

/**
 * @author phn
 * @date 2015-4-11
 * @TODO
 */
public interface IUserService {
	int isExist();

	int register(User user);

	int login(User user);

	Map<String, Object> manager(int pageSize,int currentPage);
}
