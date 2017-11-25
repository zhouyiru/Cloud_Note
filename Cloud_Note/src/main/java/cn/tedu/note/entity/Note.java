package cn.tedu.note.entity;

import java.io.Serializable;

public class Note implements Serializable {
	
	private static final long serialVersionUID = -6699267497847540276L;
	private String notebookId;
	private String noteId;
	private String id;
	private String statusId;
	private String typeId;
	private String title;
	private String body;
	private Long createTime;
	private Long LastModifyTime;
	
	public Note(String id, String notebookId, String noteId, String statusId, String typeId, String title, String body,
			Long createTime, Long lastModifyTime) {
		super();
		this.id = id;
		this.notebookId = notebookId;
		this.noteId = noteId;
		this.statusId = statusId;
		this.typeId = typeId;
		this.title = title;
		this.body = body;
		this.createTime = createTime;
		LastModifyTime = lastModifyTime;
	}

	public Note() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotebookId() {
		return notebookId;
	}

	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getLastModifyTime() {
		return LastModifyTime;
	}

	public void setLastModifyTime(Long lastModifyTime) {
		LastModifyTime = lastModifyTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LastModifyTime == null) ? 0 : LastModifyTime.hashCode());
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((noteId == null) ? 0 : noteId.hashCode());
		result = prime * result + ((notebookId == null) ? 0 : notebookId.hashCode());
		result = prime * result + ((statusId == null) ? 0 : statusId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (LastModifyTime == null) {
			if (other.LastModifyTime != null)
				return false;
		} else if (!LastModifyTime.equals(other.LastModifyTime))
			return false;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (noteId == null) {
			if (other.noteId != null)
				return false;
		} else if (!noteId.equals(other.noteId))
			return false;
		if (notebookId == null) {
			if (other.notebookId != null)
				return false;
		} else if (!notebookId.equals(other.notebookId))
			return false;
		if (statusId == null) {
			if (other.statusId != null)
				return false;
		} else if (!statusId.equals(other.statusId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "id=" + id + ", notebookId=" + notebookId + ", noteId=" + noteId + ", statusId=" + statusId
				+ ", typeId=" + typeId + ", title=" + title + ", body=" + body + ", createTime=" + createTime
				+ ", LastModifyTime=" + LastModifyTime;
	}
	
}
