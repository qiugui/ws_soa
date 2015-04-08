 package cn.edu.qiugui.service;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.ws.WebServiceContext;

import org.qiugui.dao.UserDao;
import org.qiugui.exception.UserException;
import org.qiugui.vo.User;

import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.developer.JAXWSProperties;

@WebService(endpointInterface="cn.edu.qiugui.service.IUserService",
		serviceName="UserService",
		portName="UserServicePort",
		targetNamespace="http://service.qiugui.edu.cn/",
		wsdlLocation="WEB-INF/wsdl/user.wsdl")
 public class UserServiceImpl implements IUserService {

	 private UserDao userDao = UserDao.newInstance();
	 
	 @Resource
	 private WebServiceContext ctx;
	 
	@Override
	public void add(User user) throws UserException {
		checkRegister();
		userDao.add(user);
	}

	@Override
	public void delete(String username) throws UserException {
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
	
	//权限控制
	private void checkRegister() throws UserException {
		// 获取消息头中的信息，并且进行相应的判断
		try {
			HeaderList headers = (HeaderList) ctx.getMessageContext().get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
			QName name = new QName("http://service.qiugui.edu.cn/", "licenseInfo");
			
			if (headers == null) throw new UserException("该功能需要进行权限控制！");
			
			Header header = headers.get(name, true);
			
			if (header == null) throw new UserException("该功能需要进行权限控制！");
			
			XMLStreamReader xsr = header.readHeader();
			
			User user = X2User(xsr);
			
			User tu = userDao.loadByUsername(user.getUsername());
			
			if (tu == null ) throw new UserException("你所访问的用户不是授权用户！");
			
			if (!user.getPassword().equals(tu.getPassword())) 
				throw new UserException("授权用户密码不正确！");

			
		} catch (XMLStreamException e) {
			 e.printStackTrace(); 
		}
		
	}

	private User X2User(XMLStreamReader xsr) throws XMLStreamException {
		User user = new User();
		while (xsr.hasNext()) {
			int type = xsr.next();
			if (type == XMLEvent.START_ELEMENT){
				String name = xsr.getName().toString();
				if ("username".equals(name)){
					user.setUsername(xsr.getElementText());
				} else if ("nickname".equals(name)){
					user.setNickname(xsr.getElementText());
				} else if ("password".equals(name)){
					user.setPassword(xsr.getElementText());
				}
			}
		}
		return user;
		 
	}

}

 