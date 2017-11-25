package cn.tedu.note.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.util.JsonResult;

public class AbstractController {

	public AbstractController() {
		super();
	}

	/**
	 * 在其他控制器方法执行出现异常时候
	 * 异常处理方法handleException
	 * 
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handleException(Exception e) {
		e.printStackTrace();
		return new JsonResult(e);
	}

}