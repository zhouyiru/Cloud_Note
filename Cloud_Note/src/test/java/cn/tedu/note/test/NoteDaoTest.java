package cn.tedu.note.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.entity.Note;

public class NoteDaoTest extends BaseTest {
	NoteDao dao;
	@Before 
	public void initDao(){
		dao=ctx.getBean("noteDao",NoteDao.class);	
	}
	@Test
	public void testFindNotesByNotebookId(){
		String notebookId="fa8d3d9d-2de5-4cfe-845f-951041bcc461";
		List<Map<String,Object>> list=dao.findNotesByNotebookId(notebookId);
		for(Map<String,Object> map:list){
			System.out.println(map);
		}
	}
	@Test
	public void testFingNoteById(){
		String noteId="ebd65da6-3f90-45f9-b045-782928a5e2c0";
		Note note=dao.findNoteById(noteId);
		System.out.println(note);
	}
	
	/*public void testAddNote(){
		Note note=new Note("c8d81ee5-f8cd-49e8-b2e6-ab174a926d95","","","","","","",9876545678976544444444445,68769707073222488487);
		int n=dao.addNote(note);
	}*/
}
