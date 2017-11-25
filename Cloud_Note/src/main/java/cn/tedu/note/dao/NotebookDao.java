package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.tedu.note.entity.Notebook;

public interface NotebookDao {
	List<Map<String,Object>> findNotebooksByUserId(String userId);
	Notebook findNotebookById(String notebookId);
	int countNotebookById(String notebookId);
	List<Map<String,Object>> findNotebooksByPage(
			@Param("userId")String userId,
			@Param("start")int start,
			@Param("pageSize")int pageSize,
			@Param("table") String table);
}
