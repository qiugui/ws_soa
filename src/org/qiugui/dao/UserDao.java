 package org.qiugui.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.qiugui.exception.UserException;
import org.qiugui.vo.User;
 public class UserDao {

	 private static final Map<String,User> users = new HashMap<String, User>();
	 
	 private static UserDao userDao=null;
	 
	 private UserDao(){
		 users.put("admin",new User("admin", "123", "超级管理员"));
	 }
	 
	 public static UserDao newInstance(){
		 if (userDao == null) userDao = new UserDao();
		 return userDao;
	 }
	 
	 public void add(User user) throws UserException{
		 if (user == null) throw new UserException("传入的User对象为空！");
		 if (users.containsKey(user.getUsername())) throw new UserException("用户已存在！");
		 users.put(user.getUsername(), user);
	 }
	 
	 public void delete(String username){
		 users.remove(username);
	 }
	 
	 public Set<String> listUsers(){
		 return users.keySet();
	 }
	 
	 public User loadByUsername(String userName){
		 return users.get(userName);
	 }
	 
	 public List<User> list(){
		 Set<String> keys = users.keySet();
		 List<User> list = new ArrayList<User>();
		 for (String key : keys){
			 list.add(users.get(key));
		 }
		 return list;
	 }
	 
	 public User login(String username,String password) throws UserException{
		 if (!users.containsKey(username)) throw new UserException("用户不存在！");
		 User u = users.get(username);
		 if (!password.equals(u.getPassword())){
			 throw new UserException("用户密码不正确！");
		 }
		 return u;
	 }
}

 