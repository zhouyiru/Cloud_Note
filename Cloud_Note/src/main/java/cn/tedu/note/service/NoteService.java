package cn.tedu.note.service;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;

public interface NoteService {
	List<Map<String,Object>> listNotes(String notebookId)throws NotebookNotFoundException;
	Note getNote(String noteId)throws NoteNotFoundException;
	boolean update(String noteId, String title, String body)throws NoteNotFoundException;
	public Note addNote(String id, String notebookId, String title)throws UserNotFoundException,NotebookNotFoundException;
	int deleteNotes(String... noteIds)throws NoteNotFoundException;
	
	//添加方法
	boolean addStars(String userId,int stars)throws NoteNotFoundException;
}
