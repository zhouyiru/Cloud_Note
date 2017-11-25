package cn.tedu.note.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.entity.User;

public class NotebookDaoTest extends BaseTest {
	NotebookDao dao;
	@Before 
	public void initDao(){
		dao=ctx.getBean("notebookDao",NotebookDao.class);	
	}
	@Test
	public void testFindNotebooksByUserId(){
		String userId="524f7440-7283-4b2d-8af5-4a67570e892e";
		List<Map<String,Object>> list=dao.findNotebooksByUserId(userId);
		for(Map<String,Object> map:list){
			System.out.println(map);
		}
	}
}
