package cn.tedu.note.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import cn.tedu.note.entity.User;

/**
 * 登录功能，登陆成功返回用户信息，登陆失败则抛出异常。
 * @param name 用户名
 * @param password 密码
 * @return 如果登陆成功就返回登录用户信息
 * @throws  UserNotFoundException用户名不存在
 * @throws  PasswordException 密码错误
 */
public interface UserService {
	/**
	 * UserService 中添加注册功能
	 * @param name
	 * @param nick
	 * @param password
	 * @param confirm
	 * @return 注册成功的用户信息
	 * @throws UserNotFoundException
	 * @throws PasswordException
	 */
	User login(String name,String password) throws UserNotFoundException,PasswordException;
	User regist(String name,String nick,String password,String confirm) throws UserNameException,PasswordException;
	
}
