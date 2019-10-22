package com.daddyscode.dto;

public class Folder {
	public String foldername;
	public String createddate;
	public String folder;
	public int folderid;
	public int userid;
	
	public int getFolderid() {
		return folderid;
	}
	public void setFolderid(int folderid) {
		this.folderid = folderid;
	}
	public String getFoldername() {
		return foldername;
	}
	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
}
