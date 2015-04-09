package org.phn.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.phn.bean.User;
import org.phn.dao.IUserDao;
import org.phn.util.DBUtil;

/**
 * @author phn
 * @date 2015-4-8
 * @TODO
 */
public class UserDaoImpl extends JDBCDaoSupport implements IUserDao {

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
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		User user = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement(getSql);
			pstm.setInt(1, userId);
			rs = pstm.executeQuery();
			while(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUname(rs.getString("uname"));
				user.setUpass(rs.getString("upass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("**Error**:caused at " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ "()::" + e.getMessage());
		}finally{
			DBUtil.closeConnection(conn, pstm, null);
		}
		return user;
	}
}
