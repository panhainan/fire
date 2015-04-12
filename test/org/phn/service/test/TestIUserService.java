package org.phn.service.test;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.phn.service.IUserService;
import org.phn.service.impl.UserServiceImpl;

/**
 * @author phn
 * @date 2015-4-12
 * @TODO 
 */
public class TestIUserService {
	private IUserService userService = new UserServiceImpl();
//	@Test
	public void testIsExist() {
		fail("Not yet implemented");
	}

//	@Test
	public void testRegister() {
		fail("Not yet implemented");
	}

//	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}

	@Test
	public void testManager() {
		Map<String , Object> map = new HashMap<String,Object>();
		int  pageSize = 2;
		int currentPage = 1;
		map = userService.manager(pageSize, currentPage);
		System.out.println(map.toString());
	}

}
