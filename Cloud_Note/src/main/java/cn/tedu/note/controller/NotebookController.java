package cn.tedu.note.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tedu.note.service.NotebookService;
import cn.tedu.note.util.JsonResult;

@RequestMapping("/notebook")
@Controller
public class NotebookController extends AbstractController{
	
	@Resource
	private NotebookService notebookSerive;
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(HttpSession session){
		
		String userId=(String) session.getAttribute("userId");
		List<Map<String,Object>> data=notebookSerive.listNotebooks(userId);
		/*for(Map<String,Object>map:data){
			System.out.println(map);
		}*/
		/*ObjectMapper om=new ObjectMapper();
		String str;
		try {
			str = om.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("json转化失败！");
		}*/
		return new JsonResult(data);
	}
	
	@RequestMapping("/page.do")
	@ResponseBody
public JsonResult lists(HttpSession session,Integer page){
		String userId=(String) session.getAttribute("userId");
		List<Map<String,Object>> data=notebookSerive.listNotebooks(userId, page);
		/*for(Map<String,Object>map:data){
			System.out.println(map);
		}*/
		/*ObjectMapper om=new ObjectMapper();
		String str;
		try {
			str = om.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("json转化失败！");
		}*/
		return new JsonResult(data);
	}
}
