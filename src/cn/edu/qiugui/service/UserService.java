 package cn.edu.qiugui.service;

import javax.xml.ws.Endpoint;
 public class UserService {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/ms", new UserServiceImpl());
	}

}

 