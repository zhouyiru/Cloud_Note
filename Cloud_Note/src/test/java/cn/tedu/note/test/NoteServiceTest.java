package cn.tedu.note.test;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import cn.tedu.note.entity.Note;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.service.NotebookService;

public class NoteServiceTest extends BaseTest {

		NoteService service;
		@Before
		public void initService(){
			service=ctx.getBean("noteService",NoteService.class);
		}
		@Test
		public void testListNotes(){
			String notebookId="fa8d3d9d-2de5-4cfe-845f-951041bcc461";
			List<Map<String,Object>> list=service.listNotes(notebookId);
			for(Map<String,Object> map:list){
				System.out.println(map);
			}
		}
		@Test
		public void testGetNote(){
			String noteId="fed920a0-573c-46c8-ae4e-368397846efd";
			Note note=service.getNote(noteId);
			System.out.println(note);
		}
	
		@Test
		public void testDeleteNotes(){
			String id1="";
			String id2="";
			String id3="";
			String id4="";
			int n=service.deleteNotes(id1,id2,id3,id4);
		}
		@Test
		public void testAddStars(){
			
		}
}
