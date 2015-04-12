package org.phn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.phn.bean.User;
import org.phn.dao.IUserDao;
import org.phn.dao.impl.UserDaoImpl;
import org.phn.dto.Page;
import org.phn.service.IUserService;

/**
 * @author phn
 * @date 2015-4-11
 * @TODO
 */
public class UserServiceImpl implements IUserService {
	IUserDao userDao = new UserDaoImpl();

	public int isExist() {
		return 0;
	}

	public int register(User user) {
		return 0;
	}

	public int login(User user) {
		return 0;
	}

	public Map<String, Object> manager(int pageSize, int current) {
		Page pageBean = new Page();
		int allRecords = userDao.countRow();
		int totalPages = pageBean.calculateTotalPage(pageSize, allRecords);
		int currentPage = pageBean.judgeCurrentPageIsLegal(current, totalPages);
		if (currentPage == 0) {
			return null;
		}
		int startRecord = pageBean.calculateCurrentPageStartRecord(pageSize,
				currentPage);
		pageBean.init(allRecords, currentPage, pageSize, totalPages);
		pageBean.init(allRecords, currentPage, pageSize, totalPages);
		List<User> list = userDao.list(pageSize, startRecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listUser", list);
		map.put("pageBean", pageBean);
		return map;
	}

}
