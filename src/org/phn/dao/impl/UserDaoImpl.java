package org.phn.dao.impl;

import java.util.List;

import org.phn.bean.User;
import org.phn.dao.IUserDao;

/**
 * @author phn
 * @date 2015-4-8
 * @TODO
 */
public class UserDaoImpl extends JDBCDaoSupport<User> implements IUserDao {

	public int save(User user) {
		String insertSql = "insert into t_user(uname,upass) values(?,?)";
		return super.executeInsert(insertSql, user.getUname(),user.getUpass());
	}

	public int update(User user) {
		String updateSql = "update t_user set uname=?,upass=? where id=?";
		return super.executeUpdateAndDelete(updateSql, user.getUname(),user.getUpass(),user.getId());
	}

	public int delete(int userId) {
		String deleteSql = "delete from t_user where id=?";
		return super.executeUpdateAndDelete(deleteSql,userId);
	}

	public User get(int userId) {
		String getSql = "select * from t_user where id=?";
		return super.executeGet(getSql, userId, User.class);
	}
	
	public List<User> list(int pageSize,int startRecord){
		String listSql = "select * from t_user limit ?,?";
		return super.executeList(listSql,User.class,startRecord,pageSize);
	}

	public int countRow() {
		String getCountSql = "select count(*) from t_user";
		return super.getCountRow(getCountSql);
	}
	
}
