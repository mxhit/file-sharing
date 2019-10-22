package com.daddyscode.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import com.daddyscode.constant.Queries;
import com.daddyscode.dto.Files;
import com.daddyscode.dto.Folder;
import com.daddyscode.dto.Login;
import com.daddyscode.utility.DatabaseConnection;
import com.daddyscode.utility.CloseConnection;

public class FileSharingDao extends CloseConnection implements Queries {
	Files file = new Files();
	
	
	// ****************************** AUTHENTICATION ******************************	
	public boolean getAuthentication(Login login) throws ClassNotFoundException, SQLException {
			PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
					.prepareStatement(AUTHENTICATION_QUERY);
			preparedStatement.setString(1, login.getUsername());
			preparedStatement.setString(2, login.getPassword());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				close(preparedStatement, resultSet);
				
				return true;
			}
		
		return false;
	}

	// ****************************** USER ******************************	
	public int getUser(String username) throws ClassNotFoundException, SQLException {
		int userid = 0;
		
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection().prepareStatement(GET_USER_QUERY);
		preparedStatement.setString(1, username);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			userid = resultSet.getInt("id");
			
			close(preparedStatement, resultSet);
		}
		
		return userid;
	}
	
	public ArrayList<Login> getUsers() throws ClassNotFoundException, SQLException {
		ArrayList<Login> usersList = new ArrayList<Login>();
		
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection().prepareStatement(GET_USERS_QUERY);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			Login login = new Login();
			login.setId(resultSet.getInt("id"));
			login.setUsername(resultSet.getString("username"));
			login.setPassword(resultSet.getString("password"));
			
			usersList.add(login);
		}
		
		close(preparedStatement, resultSet);
		
		return usersList;
	}
	
	// ****************************** FOLDERS ******************************
	public ArrayList<Folder> getFolder(int userid) throws SQLException, ClassNotFoundException {
		ArrayList<Folder> folderList = new ArrayList<Folder>();

		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(GET_FOLDERS_QUERY);
		preparedStatement.setInt(1, userid);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Folder folder = new Folder();
			folder.setFolderid(resultSet.getInt("id"));
			folder.setFoldername(resultSet.getString("foldername"));
			folder.setCreateddate(resultSet.getString("createddate"));
			folder.setFolder(resultSet.getString("folder"));
			folder.setUserid(resultSet.getInt("userid"));
			
			folderList.add(folder);
		}

		close(preparedStatement, resultSet);

		return folderList;
	}
	
	public void addFolder(String foldername, String createddate, String folder, int userid)
			throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(ADD_FOLDER_QUERY);
		preparedStatement.setString(1, foldername);
		preparedStatement.setString(2, createddate);
		preparedStatement.setString(3, folder);
		preparedStatement.setInt(4, userid);
		preparedStatement.executeUpdate();
		
		close(preparedStatement);
	}
	
	public void renameFolder(int folderid, String newname) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(RENAME_FOLDER_QUERY);
		preparedStatement.setString(1, newname);
		preparedStatement.setInt(2, folderid);
		preparedStatement.executeUpdate();
		
		close(preparedStatement);
	}
	
	public void deleteFolder(int folderid) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(DELETE_FOLDER_QUERY);
		preparedStatement.setInt(1, folderid);
		preparedStatement.executeUpdate();

		close(preparedStatement);
		
		PreparedStatement preparedStatement2 = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(DELETE_FILES_OF_FOLDER_QUERY);
		preparedStatement2.setInt(1, folderid);
		preparedStatement2.executeUpdate();
		
		close(preparedStatement2);
	}
	
	// ****************************** FILES ******************************
	public ArrayList<Files> getFiles(int folderid, int userid) throws SQLException, ClassNotFoundException {
		ArrayList<Files> fileList = new ArrayList<Files>();
	
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(GET_FILES_QUERY);
		preparedStatement.setInt(1, folderid);
		preparedStatement.setInt(2, userid);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			Files file = new Files();
			file.setFileid(resultSet.getInt("id"));
			file.setFolderid(resultSet.getInt("folderid"));
			file.setFilename(resultSet.getString("filename"));
			file.setCreateddate(convertDate(resultSet.getString("createddate")));
			file.setFile(resultSet.getString("file"));
			file.setLastmodified(convertDate(resultSet.getString("lastmodified")));
			file.setExtension(extractExtension(resultSet.getString("file")));
			file.setIcon(getIcon(extractExtension(resultSet.getString("file"))));
			file.setUserid(userid);
			
			fileList.add(file);
		}
		
		close(preparedStatement, resultSet);
	
		return fileList;
	}
	
	public void addFiles(String filename, int folderid, int userid) throws SQLException, ClassNotFoundException {
		String file = extractName(filename);
		String date = extractDate(filename);
		String extension = extractExtension(filename);
		
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(ADD_FILES_QUERY);
		preparedStatement.setInt(1, folderid);
		preparedStatement.setString(2, filename);
		preparedStatement.setString(3, date);
		preparedStatement.setString(4, file);
		preparedStatement.setString(5, date);
		preparedStatement.setInt(6, userid);
		preparedStatement.executeUpdate();
		
		this.file.setFolderid(folderid);
		this.file.setFilename(filename);
		this.file.setCreateddate(date);
		this.file.setFile(file);
		this.file.setLastmodified(date);
		this.file.setExtension(extension);
		this.file.setIcon(getIcon(extension));
		this.file.setUserid(userid);
		
		close(preparedStatement);
	}
	
	
	public String renameFile(String newname, int fileid) throws SQLException, ClassNotFoundException {
		String filename = "";
		
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(GET_FILE_QUERY);
		preparedStatement.setInt(1, fileid);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			filename = resultSet.getString("filename");
		}
		
		close(preparedStatement);
		
		String extension = filename.substring(filename.lastIndexOf("."));
		String renamed = newname.concat(extension);
		
		Instant instant = Instant.now();
		String modifiedtime = String.valueOf(instant.toEpochMilli());
		
		PreparedStatement preparedStatement2 = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(RENAME_FILE_QUERY);
		preparedStatement2.setString(1, renamed);
		preparedStatement2.setString(2, modifiedtime);
		preparedStatement2.setInt(3, fileid);
		preparedStatement2.executeUpdate();
		
		close(preparedStatement2);
		
		return renamed;
	}
	
	public void deleteFile(int fileid) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection()
				.prepareStatement(DELETE_FILE_QUERY);
		preparedStatement.setInt(1, fileid);
		preparedStatement.executeUpdate();
		
		close(preparedStatement);
	}
	
	public void shareFile(int sharefileid, int shareduserid) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = DatabaseConnection.getDatabase().getConnection().prepareStatement(ADD_SHARE_INFO_QUERY);
		preparedStatement.setInt(1, sharefileid);
		preparedStatement.setInt(2, shareduserid);
		preparedStatement.executeUpdate();
		
		close(preparedStatement);
	}
	
	// ****************************** MISC ******************************	
	public String extractName(String filename) {
		return filename.substring(13);
	}
	
	public String extractDate(String filename) {
		return filename.substring(0, 13);
	}
	
	public String extractExtension(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}
	
	public String convertDate(String date) {
		long milliseconds = Long.valueOf(date);
		
		Date d = new Date(milliseconds);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(d);
	}
	
	public String getIcon(String extension) {
		String icon;
		
		switch (extension) {
		case ".css":
			icon = "css.svg";
			break;

		case ".dll":
			icon = "dll.svg";
			break;

		case ".doc":
			icon = "doc.svg";
			break;

		case ".docx":
			icon = "doc.svg";
			break;

		case ".exe":
			icon = "exe.svg";
			break;

		case ".gif":
			icon = "gif.svg";
			break;

		case ".html":
			icon = "html.svg";
			break;

		case ".jpg":
			icon = "jpg.svg";
			break;

		case ".jpeg":
			icon = "jpeg.svg";
			break;

		case ".mkv":
			icon = "mkv.svg";
			break;

		case ".mp3":
			icon = "mp3.svg";
			break;

		case ".pdf":
			icon = "pdf.svg";
			break;

		case ".php":
			icon = "php.svg";
			break;

		case ".png":
			icon = "png.svg";
			break;

		case ".ppt":
			icon = "ppt.svg";
			break;

		case ".pptx":
			icon = "ppt.svg";
			break;

		case ".rar":
			icon = "rar.svg";
			break;

		case ".svg":
			icon = "svg.svg";
			break;

		case ".tar":
			icon = "tar.svg";
			break;

		case ".txt":
			icon = "txt.svg";
			break;

		case ".xls":
			icon = "xls.svg";
			break;

		case ".xlsx":
			icon = "xls.svg";
			break;

		case ".zip":
			icon = "zip.svg";
			break;

		default:
			icon = "file.svg";
			break;
		}
		
		return icon;
	}
}