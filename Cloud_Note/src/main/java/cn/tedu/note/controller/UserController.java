package cn.tedu.note.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tedu.note.entity.User;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.service.PasswordException;
import cn.tedu.note.service.UserNameException;
import cn.tedu.note.service.UserNotFoundException;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
	
	@Resource
	private UserService userService;
	@Resource
	private NotebookService notebookSerive;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(String name,String password,HttpSession session){
			User user=userService.login(name, password);
			//登录成功时候，将user信息保存到session
			//用于在过滤器中检查登录情况
			session.setAttribute("userId",user.getId().toString());
			session.setAttribute("loginUser", user);
			return new JsonResult(user);
	}
	/**
	 * 
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public JsonResult handleUserNotFound(UserNotFoundException e){
		e.printStackTrace();
		return new JsonResult(2,e);
	}
	@ExceptionHandler(PasswordException.class)
	@ResponseBody
	public JsonResult handlePassword(PasswordException e){
		e.printStackTrace();
		return new JsonResult(3,e);
	}
	@ExceptionHandler(UserNameException.class)
	@ResponseBody
	public JsonResult handleUserName(UserNameException e){
		e.printStackTrace();
		return new JsonResult(4,e);
	}
	@RequestMapping("/regist.do")
	@ResponseBody
	public JsonResult regist(String name,String nick,String password,String confirm){
		User user=userService.regist(name, nick, password, confirm);
		System.out.println(user);
		return new JsonResult(user);
	}
	
	@RequestMapping("/heartbeat.do")
	@ResponseBody
	public JsonResult heartbeat(){
	    Object ok = "ok";
	    return new JsonResult(ok);
	}
	/*
	 * @ResponseBody 注解会自动处理控制返回值
	 * 1.如果是JavaBean（数组，集合）返回JSON
	 * 2.如果是byte数字，则将byte数组直接装入响应消息的body
	 */
	//produces="image/ong"用于设置content-type
	@RequestMapping(value="/image.do",produces="image/png" )
	@ResponseBody
	public byte[] image() throws Exception{
		return createPng();
	}
	@RequestMapping(value="/downloadimg.do" ,produces="application/octet-stream")
	@ResponseBody
	public byte[] download(HttpServletResponse res) throws IOException{
		res.setHeader("Content-Disposition", "attachment;filename=\"demo.png\"");
		return createPng();
	}
	/*
	 * 创建个图片。并且编码为png格式，返回编码以后的数据
	 */
	private byte[] createPng() throws IOException{
		BufferedImage img=new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		//在图片绘制内容
		img.setRGB(100, 40, 0xffffff);
		//将图片编码为png
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		out.close();
		byte[] png=out.toByteArray();
		return png;
	}
	@RequestMapping(value="/excel.do" ,produces="application/octet-stream")
	@ResponseBody
	public byte[] download1(HttpServletResponse res) throws IOException{
		res.setHeader("Content-Disposition", "attachment;filename=\"demo.excel\"");
		return createExcel();
	}
	private byte[] createExcel()throws IOException{
		//创建工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet=workbook.createSheet("Demo");
		//在工作薄中创建数据行
		HSSFRow row=sheet.createRow(0);
		//创建行中的格子
		HSSFCell cell=row.createCell(0);
		cell.setCellValue("hello world");
		//将Excel文件保存在byte数组
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		workbook.write(out);
		out.close();
		return out.toByteArray();
	}
	
	@RequestMapping("upload.do")
	@ResponseBody
	public JsonResult upload(MultipartFile userfile1,MultipartFile userfile2) throws IllegalStateException, IOException{
		//Spring MVC中可以利用MultipartFile
		//接收上载文件，文件中的一切数据
		//都可以从MultipartFile对象中找到
		String  file1=userfile1.getOriginalFilename();
		String file2=userfile2.getOriginalFilename();
		/**
		 * 3种方法：
		 * 1、transferTo（目标文件）(将文件直接保存到目标文件，可以处理大文件)
		 * 2、userfile1.getBytes（）获取文件的全部数据，将文件全部读取到内存，适合处理小文件
		 * 3、userfile1.getInputStream（）获取上载文件的流，适合处理大文件
		 * 
		 */
		//保存的目标文件夹：
		File dir=new File("D:/demo1");
		dir.mkdir();
		
		File f1=new File(dir,file1);
		File f2=new File(dir,file2);
		
		//第一种保存文件
		//userfile1.transferTo(f1);
		//userfile2.transferTo(f2);
		
		//第三种 利用流复制数据
		InputStream in=userfile1.getInputStream();
		FileOutputStream fos=new FileOutputStream(f1);
		int b;
		while((b=in.read())!=-1){
			fos.write(b);
		}
		in.close();
		fos.close();
		
		InputStream in2=userfile2.getInputStream();
		FileOutputStream fos2=new FileOutputStream(f2);
		byte[] buf=new byte[8*1024];
		int n;
		while((n=in2.read())!=-1){
			fos2.write(buf,0,n);
		}
		in2.close();
		fos2.close();
		return new JsonResult(true);
	}
}
