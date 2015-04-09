package org.phn.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.phn.bean.User;
import org.phn.dao.IUserDao;
import org.phn.util.DBUtil;

/**
 * @author phn
 * @date 2015-4-8
 * @TODO
 */
public class UserDaoImpl implements IUserDao {

	public int save(User user) {
		String insertSql = "insert into t_user(uname,upass) values(?,?)";
		Connection conn = DBUtil.getConnection();
		int insertedUserId = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS);
			// 注意参数从1开始
			pstm.setString(1, user.getUname());
			pstm.setString(2, user.getUpass());
			pstm.executeUpdate();
			rs = pstm.getGeneratedKeys();
			while (rs.next()) {
				insertedUserId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("**Error**:caused at " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ "()::" + e.getMessage());
		} finally {
			DBUtil.closeConnection(conn, pstm, rs);
		}
		return insertedUserId;
	}

	public int update(User user) {
		String updateSql = "update t_user set uname=?,upass=? where id=?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int updateResult = 0;
		try {
			pstm = conn.prepareStatement(updateSql);
			pstm.setString(1, user.getUname());
			pstm.setString(2, user.getUpass());
			pstm.setInt(3, user.getId());
			updateResult = pstm.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("**Error**:caused at " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ "()::" + e.getMessage());
		}finally{
			DBUtil.closeConnection(conn, pstm, null);
		}
		return updateResult;
	}

	public int delete(int userId) {
		String deleteSql = "delete from t_user where id=?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int deleteResult = 0;
		try {
			pstm = conn.prepareStatement(deleteSql);
			pstm.setInt(1, userId);
			deleteResult = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("**Error**:caused at " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ "()::" + e.getMessage());
		}finally{
			DBUtil.closeConnection(conn, pstm, null);
		}
		return deleteResult;
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
