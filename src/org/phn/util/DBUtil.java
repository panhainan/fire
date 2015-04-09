package org.phn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * @author phn
 * @date 2015-4-8
 * @TODO 数据库操作工具类
 */
public class DBUtil {
	static ResourceBundle rbundle = ResourceBundle.getBundle("db");
	private static String driverName = rbundle.getString("driverClassName");
	private static String dbUser = rbundle.getString("username");
	private static String dbPass = rbundle.getString("password");
	private static String dbUrl = rbundle.getString("url");

	/**
	 * @date 2015-4-8
	 * @TODO 获取数据库连接
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			// 这里使用这种方法已经指定了new出来的Driver是mysql的驱动
			// DriverManager.registerDriver(new Driver());
			Class.forName(driverName).newInstance();
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			if (conn != null) {
				System.out.println("数据库连接成功！conn=" + conn);
				return conn;
			}
		} catch (InstantiationException e) {
			 e.printStackTrace();
			System.out.println("**Error**:caused at " + DBUtil.class + "."
					+ new Throwable().getStackTrace()[0].getMethodName() + "::"
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			 e.printStackTrace();
			System.out.println("**Error**:caused at " + DBUtil.class + "."
					+ new Throwable().getStackTrace()[0].getMethodName() + "::"
					+ e.getMessage());
		} catch (ClassNotFoundException e) {
			 e.printStackTrace();
			System.out.println("**Error**:caused at " + DBUtil.class + "."
					+ new Throwable().getStackTrace()[0].getMethodName() + "::"
					+ e.getMessage());
		} catch (SQLException e) {
			 e.printStackTrace();
			System.out.println("**Error**:caused at " + DBUtil.class + "."
					+ new Throwable().getStackTrace()[0].getMethodName() + "::"
					+ e.getMessage());
		}
		System.out.println("**Error**:数据库连接失败！");
		return null;
	}

	public static void closeConnection(Connection conn, PreparedStatement pstm,
			ResultSet rs) {
		try { // 捕捉异常
			try {
				if (rs != null) { // 当ResultSet对象的实例rs不为空时
					rs.close(); // 关闭ResultSet对象
				}
			} finally {
				try {
					if (pstm != null) { // 当Statement对象的实例stmt不为空时
						pstm.close(); // 关闭Statement对象
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null) { // 当Connection对象的实例conn不为空时
						conn.close(); // 关闭Connection对象
					}
				}
			}
		} catch (Exception e) {
			System.out.println("**Error**:caused at " + DBUtil.class + "."
					+ new Throwable().getStackTrace()[0].getMethodName() + "::"
					+ e.getMessage());// 输出异常信息
		}
	}

	public static void main(String[] args) {
		try {
			Connection conn = getConnection();
			String insertSql = "INSERT INTO t_user(uname,upass) VALUES(?,?)";
			if (conn != null) {
				PreparedStatement pstm = conn.prepareStatement(insertSql);
				pstm.setString(1, "234");
				pstm.setString(2, "234");
				pstm.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("**Error**:" + e.getMessage());
		}

	}
}
