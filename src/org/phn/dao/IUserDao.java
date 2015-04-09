package org.phn.dao;

import org.phn.bean.User;


/**
 * @author phn
 * @date 2015-4-8
 * @TODO 
 */
public interface IUserDao {
	int save(User user);
	
	int update(User user);
	
	int delete(int userId);
	
	User get(int userId);
	
}
