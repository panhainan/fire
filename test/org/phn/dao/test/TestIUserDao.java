package org.phn.dao.test;

import java.util.List;

import org.junit.Test;
import org.phn.bean.User;
import org.phn.dao.IUserDao;
import org.phn.dao.impl.UserDaoImpl;

/**
 * @author phn
 * @date 2015-4-8
 * @TODO 测试UserDao的方法
 */
public class TestIUserDao {

	IUserDao userDao = new UserDaoImpl();

	// @Test
	public void testSave() {
		User user = new User();
		user.setUname("潘海南" + (int) (Math.random() * 100));
		user.setUpass("123" + (int) (Math.random() * 100));
		int uid = userDao.save(user);
		user.setId(uid);
		System.out.println(user.toString());
		System.out.println("Test " + this.getClass() + "."
				+ new Throwable().getStackTrace()[0].getMethodName()
				+ " Result : " + uid);
	}

	// @Test
	public void testUpdate() {
		User user = new User();
		user.setId(3);
		user.setUname("phn" + (int) (Math.random() * 100));
		user.setUpass("321" + (int) (Math.random() * 100));
		System.out.println(user.toString());
		int result = userDao.update(user);
		System.out.println("Test " + this.getClass() + "."
				+ new Throwable().getStackTrace()[0].getMethodName()
				+ " Result : " + result);
	}

	// @Test
	public void testDelete() {
		int result = userDao.delete(2);
		System.out.println("Test " + this.getClass() + "."
				+ new Throwable().getStackTrace()[0].getMethodName()
				+ " Result : " + result);
	}

//	@Test
	public void testGet() {
		User user = userDao.get(3);
		if (user != null) {
			System.out.println("Test " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ " Result : " + user.toString());
		} else {
			System.out.println("**Error**:查找失败！");
		}

	}

	@Test
	public void testList() {
		List<User> list = userDao.list(2,1);
		if (list != null) {
			System.out.println("Test " + this.getClass() + "."
					+ new Throwable().getStackTrace()[0].getMethodName()
					+ " Result : ");
			for(User user:list){
				System.out.println(user.toString());
			}
		} else {
			System.out.println("**Error**:查找失败！");
		}

	}
	@Test
	public void testCount(){
		long count = userDao.countRow();
		System.out.println(count);
	}
}
