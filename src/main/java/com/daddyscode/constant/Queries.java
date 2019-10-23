package com.daddyscode.constant;

public interface Queries {
	// ****************************** AUTHENTICATION ******************************
	public static final String AUTHENTICATION_QUERY = "SELECT * FROM FSuser WHERE username = ? AND password = ?";
	
	// ****************************** VIEW ******************************
	static final String GET_FOLDERS_QUERY = "SELECT * FROM FSfolder where userid = ?";
	static final String GET_FILES_QUERY = "SELECT * FROM FSfile WHERE folderid = ? AND userid = ?";
	static final String GET_FILE_QUERY = "SELECT filename FROM FSfile WHERE id = ?";
	static final String DOWNLOAD_FILE_QUERY = "SELECT filename FROM FSfile WHERE id = ?";
	static final String GET_USER_QUERY = "SELECT id FROM FSuser WHERE username = ?";
	static final String GET_USERS_QUERY = "SELECT * FROM FSuser";
	
	// ****************************** INSERT ******************************	
	static final String ADD_USER_QUERY = "INSERT INTO FSuser (username, password, fullname, email) VALUES (?, ?, ?, ?)";
	static final String ADD_FOLDER_QUERY = "INSERT INTO FSfolder (foldername, createddate, folder, userid) VALUES (?, ?, ?, ?)";
	static final String ADD_FILES_QUERY = "INSERT INTO FSfile (folderid, filename, createddate, file, lastmodified, userid) VALUES (?, ?, ?, ?, ?, ?)";
	static final String ADD_SHARE_INFO_QUERY = "INSERT INTO FSshare (fileid, sharedwith) VALUES (?, ?)";

	// ****************************** UPDATE ******************************	
	static final String RENAME_FOLDER_QUERY = "UPDATE FSfolder SET folder = ? WHERE id = ?";
	static final String RENAME_FILE_QUERY = "UPDATE FSfile SET file = ?, lastmodified = ? WHERE id = ?";
	
	// ****************************** DELETE ******************************	
	static final String DELETE_FOLDER_QUERY = "DELETE FROM FSfolder WHERE id = ?";
	static final String DELETE_FILE_QUERY = "DELETE FROM FSfile WHERE id = ?";
	static final String DELETE_FILES_OF_FOLDER_QUERY = "DELETE from FSfile WHERE folderid = ?";
}
