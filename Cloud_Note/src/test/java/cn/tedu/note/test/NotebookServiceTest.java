package cn.tedu.note.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;



import cn.tedu.note.service.NotebookService;

public class NotebookServiceTest extends BaseTest{
	NotebookService service;
	@Before
	public void initService(){
		service=ctx.getBean("notebookService",NotebookService.class);
	}
	@Test
	public void testListNotebooks(){
		String userId="03590914-a934-4da9-ba4d-b41799f917d1";
		List<Map<String,Object>> list=service.listNotebooks(userId);
		for(Map<String,Object> map:list){
			System.out.println(map);
		}
	}
	@Test
	public void testListNotebooksPages(){
		String userId="03590914-a934-4da9-ba4d-b41799f917d1";
		List<Map<String,Object>> list=service.listNotebooks(userId,0);
		for(Map<String,Object> map:list){
			System.out.println(map);
		}
	}
}
