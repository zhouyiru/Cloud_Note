package cn.tedu.note.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import cn.tedu.note.service.NotebookNotFoundException;
import cn.tedu.note.service.NotebookNoteFoundException;
import cn.tedu.note.service.UserNotFoundException;
import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.StarsDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Notebook;
import cn.tedu.note.entity.Stars;
import cn.tedu.note.entity.User;
import cn.tedu.note.service.NoteNotFoundException;
import cn.tedu.note.service.NoteService;
@Service("noteService")

public class NoteServiceImpl implements NoteService{
	@Resource 
    private NoteDao noteDao;

    @Resource
    private NotebookDao notebookDao;
    
    @Resource
    private UserDao userDao;
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookNotFoundException {
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("ID为空！");
		}
//		Notebook notebook=notebookDao.findNotebookById(notebookId);
//		if(notebook==null){
//			throw new NotebookNoteFoundException("笔记本不存在！");
//		}
		int n=notebookDao.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNoteFoundException("笔记本不存在！");
		}
		return noteDao.findNotesByNotebookId(notebookId);
//		return noteDao.findNotes(null, notebookId, null);
	}
	
	public Note getNote(String noteId) throws NoteNotFoundException {
		if(noteId==null||noteId.trim().isEmpty()){
			throw new NoteNotFoundException("ID为空");
		}
		Note note=noteDao.findNoteById(noteId);
		if(note==null){
			throw new NoteNotFoundException("笔记不存在！");
		}
		return note;
	}
	public boolean update(String noteId, String title, String body) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("ID不能空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null){
			throw new NoteNotFoundException("没有对应的笔记");
		}
		Note data = new Note();
		if(title!=null && !title.equals(note.getTitle())){
			data.setTitle(title);
		}
		if(body!=null && !body.equals(note.getBody())){
			data.setBody(body);
		}
		data.setId(noteId);
		data.setLastModifyTime(System.currentTimeMillis());
		System.out.println(data); 
		int n = noteDao.updateNote(data);
		return n==1;
	}
	
	public Note addNote(String userId, 
			String notebookId, String title)
			throws UserNotFoundException, 
			NotebookNotFoundException {
	
		if(userId==null||userId.trim().isEmpty()){
			throw new UserNotFoundException("ID空");
		}
		User user=userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("木有人");
		}
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("ID空");
		}
		int n=notebookDao.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("没有笔记本");
		}
		if(title==null || title.trim().isEmpty()){
			title="葵花宝典";
		}
		String id = UUID.randomUUID().toString();
		String statusId = "0";
		String typeId = "0";
		String body = "";
		long time=System.currentTimeMillis();
		Note note = new Note(id, notebookId,
			userId, statusId, typeId, title, 
			body, time, time);
		n = noteDao.addNote(note);
		if(n!=1){
			throw new NoteNotFoundException("保存失败");
		}
		return note;
	}
	
	@Transactional
	public int deleteNotes(String... noteIds) throws NoteNotFoundException {
		for(String id:noteIds){
			int n=noteDao.deleteNoteById(id);
			if(n!=1){
				throw new NoteNotFoundException("删除本条失败！");
			}
		}
		return noteIds.length;
	}
	
	@Resource
	
	private StarsDao starsDao;
	@Transactional
	public boolean addStars(String userId, int stars) throws NoteNotFoundException {
		if(userId==null||userId.trim().isEmpty()){
			throw new UserNotFoundException("用户不存在");
		}
		User user=userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("没有人");
		}
		Stars st=starsDao.findStarsByUserId(userId);
		if(st==null){
			String id=UUID.randomUUID().toString();
			st=new Stars(id,userId,stars);
			int n=starsDao.insertStars(st);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}else{//如果有星星，就在现有的星星数量上增加
			
			int n=st.getStars()+stars;
			if(n<0){
				throw new RuntimeException("扣分太多了");
			}
			st.setStars(n);
			n=starsDao.updateStars(st);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}
		return true;
	}
}
