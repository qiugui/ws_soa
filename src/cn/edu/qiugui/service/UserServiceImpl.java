 package cn.edu.qiugui.service;

import java.util.List;

import javax.jws.WebService;

import org.qiugui.dao.UserDao;
import org.qiugui.exception.UserException;
import org.qiugui.vo.User;

@WebService(endpointInterface="cn.edu.qiugui.service.IUserService",
		serviceName="UserService",
		portName="UserServicePort",
		targetNamespace="http://service.qiugui.edu.cn/",
		wsdlLocation="WEB-INF/wsdl/user.wsdl")
 public class UserServiceImpl implements IUserService {

	 private UserDao userDao = UserDao.newInstance();
	 
	@Override
	public void add(User user) throws UserException {
		checkRegister();
		userDao.add(user);
	}

	private void checkRegister() {
		// 获取消息头中的信息，并且进行相应的判断
		 
	}

	@Override
	public void delete(String username) {
		checkRegister();
		userDao.delete(username);
	}

	@Override
	public List<User> list() {
		return userDao.list();

	}

	@Override
	public User login(String username, String password) throws UserException {
		return userDao.login(username, password);

	}

}

 