package cn.tedu.note.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.naming.NameNotFoundException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;
import cn.tedu.note.service.PasswordException;
import cn.tedu.note.service.UserNameException;
import cn.tedu.note.service.UserNotFoundException;
import cn.tedu.note.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	@Value("#{jdbc.salt}")
	private String salt;
	public User login(String name, String password) throws UserNotFoundException, PasswordException {
		
		if(password==null||password.trim().isEmpty()){
			
			throw new PasswordException("密码为空");
		}
		if(name==null||name.trim().isEmpty()){
			throw new UserNotFoundException("用户名不能为空");
		}
		User user=userDao.findUserByName(name.trim());
		if(user==null){
			throw new UserNotFoundException("用户名错误!");
		}
		String pwd=DigestUtils.md5Hex(password+salt);
		if(pwd.equals(user.getPassword())){
			return user;
		}
		throw new PasswordException("密码错误!");
	}
	public User regist(String name, String nick, String password, String confirm)
			throws UserNameException, PasswordException {
		//检验name,不能重复
		if(name==null||name.trim().isEmpty()){
			throw new UserNameException("不能为空!");
		}
		System.out.println(name);
		User one=userDao.findUserByName(name);
		if(one!=null){
			throw new UserNameException("已注册!");
		}
		//检查密码
		if(password==null||password.trim().isEmpty()){
			throw new PasswordException("密码不能为空!");
		}
		if(!password.equals(confirm)){
			throw new PasswordException("确认密码不一致!");
		}
		//检查nick
		if(nick==null||nick.trim().isEmpty()){
			nick=name;
		}
		String id=UUID.randomUUID().toString();
		String token="";
		password=DigestUtils.md5Hex(password+salt);
		User user=new User(id,name,password,token,nick);
		int n=userDao.addUser(user);
		if(n!=1){
			throw new RuntimeException("添加失败!");
		}
		return user;
	}
	
}
