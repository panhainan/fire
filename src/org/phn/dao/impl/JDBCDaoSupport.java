package org.phn.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.phn.util.DBUtil;

/**
 * @author phn
 * @date 2015-4-9
 * @TODO
 */
public class JDBCDaoSupport {

	/**
	 * @date 2015-4-9
	 * @TODO 执行插入语句
	 * @param insertSql
	 * @param params
	 * @return 插入的数据的id
	 */
	public int executeInsert(String insertSql, Object... params) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int insertId = 0;
		try {
			pstm = conn.prepareStatement(insertSql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			setParams(pstm, params);
			pstm.executeUpdate();
			rs = pstm.getGeneratedKeys();
			while (rs.next()) {
				insertId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("**Error**:caused at " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ "()::" + e.getMessage());
		} finally {
			DBUtil.closeConnection(conn, pstm, rs);
		}
		return insertId;
	}
	/**
	 * @date 2015-4-9
	 * @TODO 执行更新语句或者删除语句
	 * @param updateSql
	 * @param params
	 * @return 数据库受影响的行数
	 */
	public int executeUpdateAndDelete(String updateSql , Object...params){
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		int updateResult = 0;
		try {
			pstm = conn.prepareStatement(updateSql);
			setParams(pstm, params);
			updateResult = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("**Error**:caused at " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ "()::" + e.getMessage());
		} finally {
			DBUtil.closeConnection(conn, pstm, null);
		}
		return updateResult;
	}
	
	

	/**
	 * @date 2015-4-9
	 * @TODO 设置SQL语句中的？参数
	 * @param pstm
	 * @param params
	 * @throws SQLException
	 */
	private void setParams(PreparedStatement pstm, Object[] params)
			throws SQLException {
		if (params == null | params.length == 0)
			return;
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			if (param == null) {
				pstm.setNull(i + 1, Types.NULL);
			} else if (param instanceof Integer) {
				pstm.setInt(i + 1, (Integer) param);
			} else if (param instanceof Double) {
				pstm.setDouble(i + 1, (Double) param);
			} else if (param instanceof Long) {
				pstm.setLong(i + 1, (Long) param);
			} else if (param instanceof String) {
				pstm.setString(i + 1, (String) param);
			} else if (param instanceof Boolean) {
				pstm.setBoolean(i + 1, (Boolean) param);
			} else if (param instanceof Date) {
				pstm.setDate(i + 1, (Date) param);
			} else if (param instanceof Timestamp) {
				pstm.setTimestamp(i + 1, (Timestamp) param);
			}

		}
	}

}
