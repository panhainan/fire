package org.phn.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.phn.util.DBUtil;

/**
 * @author phn
 * @param <T>
 * @date 2015-4-9
 * @TODO
 */
public class JDBCDaoSupport<T> {

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
	public int executeUpdateAndDelete(String updateSql, Object... params) {
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
	 * @date 2015-4-10
	 * @TODO 通过数据库标识字段id查找
	 * @param sql
	 * @param id
	 * @param obj
	 * @return T
	 */
	public T executeGet(String sql, int id, Class<T> obj) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		T o = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			o = obj.newInstance();
			if (rs.next()) {
				o = setTableToEntity(obj, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * @date 2015-4-10
	 * @TODO 根据参数params获取list
	 * @param sql
	 * @param c
	 * @param params
	 * @return List
	 */
	public List<T> executeList(String sql, Class<T> c, Object... params) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> listObjects = null;
		T o = null;
		try {
			pstm = conn.prepareStatement(sql);
			setParams(pstm, params);
			rs = pstm.executeQuery();
			listObjects = new ArrayList<T>();
			while (rs.next()) {
				o = c.newInstance();
				o = setTableToEntity(c, rs);
				listObjects.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return listObjects;
	}

	/**
	 * @date 2015-4-10
	 * @TODO 将数据库中查询出来的结果集ResultSet转化为实体
	 * @param c
	 * @param rs
	 * @return T
	 */
	private T setTableToEntity(Class<T> c, ResultSet rs) {
		T o = null;
		try {
			o = c.newInstance();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columCount = rsmd.getColumnCount();
			String[] columName = new String[columCount];
			String[] columClassName = new String[columCount];
			for (int i = 0; i < columCount; i++) {
				columName[i] = rsmd.getColumnName(i + 1);
				Class<?> paramType = c.getDeclaredField(columName[i]).getType();
				StringBuilder sb = new StringBuilder(columName[i]);
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
				String attributeSetName = "set" + sb.toString();
				Method md = c.getMethod(attributeSetName, paramType);
				columClassName[i] = rsmd.getColumnClassName(i + 1);
				if ("java.lang.Integer".equals(columClassName[i])) {
					md.invoke(o, rs.getInt(sb.toString()));
				} else if ("java.lang.String".equals(columClassName[i])) {
					md.invoke(o, rs.getString(sb.toString()));
				} else if ("java.lang.Double".equals(columClassName[i])) {
					md.invoke(o, rs.getDouble(sb.toString()));
				} else if ("java.sql.Date".equals(columClassName[i])) {
					md.invoke(o, rs.getDate(sb.toString()));
				} else if ("java.lang.Boolean".equals(columClassName[i])) {
					md.invoke(o, rs.getBoolean(sb.toString()));
				} else if ("java.lang.Float".equals(columClassName[i])) {
					md.invoke(o, rs.getFloat(sb.toString()));
				} else if ("java.sql.Time".equals(columClassName[i])) {
					md.invoke(o, rs.getTime(sb.toString()));
				} else if ("java.sql.Timestamp".equals(columClassName[i])) {
					md.invoke(o, rs.getTimestamp(sb.toString()));
				} else if ("java.lang.Object".equals(columClassName[i])) {
					md.invoke(o, rs.getObject(sb.toString()));
				} else if ("java.math.BigDecimal".equals(columClassName[i])) {
					md.invoke(o, rs.getBigDecimal(sb.toString()));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return o;
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
