<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>File Share</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
        <link rel="icon" type="image/png" href="resources/images/favicon.ico"/>
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/vendor/animate/animate.css">
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/vendor/animsition/css/animsition.min.css">
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/vendor/select2/select2.min.css">
    <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="resources/vendor/daterangepicker/daterangepicker.css">
    <!--===============================================================================================-->
    	<link rel="stylesheet" type="text/css" href="resources/css/main.css">
        <link rel="stylesheet" type="text/css" href="resources/css/util.css">
    <!--===============================================================================================-->
	</head>
	<body style="background-color: white;">
		<%@ include file="header.jsp" %>
	
		<!-- file upload button -->
		<button type="button" style="float: right; padding: 5px;" onclick="fileUploadModal()">
			<img src="resources/images/icons/add-file.svg"
				class="file-upload-icon">
		</button>
		
		<!-- file upload modal -->
		<div id="fileUploadModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
	
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
						<form action="./fileupload" method="POST" enctype="multipart/form-data">
							<input type="file" name="file" class="file" id="file">
							<button type="button" class="upload-file-btn" onclick="uploadFile()">Upload</button>
						</form>
					</div>
				</div>
	
			</div>
		</div>
		
		<!-- rename file modal -->
		<div id="renameFileModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
	
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
						<form action="./renamefile" method="get">
							<input type="text" name="renamefile" class="renamefile" id="renamefile" placeholder="file.ext">
							<input type="hidden" name="renamefileid" id="renamefileid">
							<button type="button" class="rename-file-btn" onclick="renameFile()">Rename</button>
						</form>
					</div>
				</div>
	
			</div>
		</div>
		
		<!-- delete file modal -->
		<div id="deleteFileModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
	
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h5 style="padding: 10px;">Confirm delete?</h5>
					</div>
					<div class="modal-body" id="delete-modal-body">
						<form action="./deletefile" method="get">
							<input type="hidden" name="deletefileid" id="deletefileid">
							<button type="button" class="delete-file-btn" onclick="deleteFile()">Delete</button>
						</form>
					</div>
				</div>
	
			</div>
		</div>
		
		<!-- share file modal -->
		<div id="shareFileModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
	
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
					
						<form action="./sharefile" method="post" class="sharefile-form">
						
							<input list="users" name="sharedusername" class="sharedusername" placeholder="Select a user" autocomplete="off">
							<datalist id="users">
								<c:forEach var="k" items="${users}">
									<option value="${k.getFullname()}">${k.getFullname()}</option>
								</c:forEach>
							</datalist>
							<select class="permission" name="permission">
								<option value="" disabled="disabled" selected="selected">Permission</option>
								<option value="view">View</option>
								<option value="edit">Edit</option>
							</select>

							<input type="hidden" name="sharefileid" id="sharefileid">
							<button type="button" class="share-file-btn" onclick="shareFile()">Share</button>
						</form>
					</div>
				</div>
	
			</div>
		</div>
		
		<!-- grid -->
		<table class="list-view">
			<tr>
				<th id="file-col">Your Files</th>
				<th>Last Modified</th>
				<th>Actions</th>
			</tr>
			
			<c:forEach var="j" items="${files}">
				<tr>
					<!-- file -->
					<td id="file-col">
						<img src="resources/images/icons/${j.getIcon()}" class="file-icon" id="file-icon" style="margin: 0px 10px">
						<a href="resources/files/${j.getFilename()}" target="_blank" rel="noopener noreferrer">${j.getFile()}</a>
					</td>
					
					<!-- last modified date -->
					<td>${j.getLastmodified()}</td>
					
					<!-- actions -->
					<td>
						<a onclick="renameFileModal(${j.getFileid()})" class="rename"><img src="resources/images/icons/rename.svg" class="rename-icon"></a>
						<a onclick="deleteFileModal(${j.getFileid()})" class="delete"><img src="resources/images/icons/delete.svg" class="delete-icon"></a>
			 			<a href="" download="${j.getFilename()}" class="download"> <img src="resources/images/icons/download.svg" class="download-icon"></a>
		 				<a onclick="shareFileModal(${j.getFileid()})" class="share"><img src="resources/images/icons/share.svg" class="share-icon"></a>
					</td>
				</tr>			
			</c:forEach>
		</table>
		
		<%@ include file="footer.jsp" %>
		<!--===============================================================================================-->
        	<script type="text/javascript" src="resources/vendor/jquery/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        	<script type="text/javascript" src="resources/vendor/animsition/js/animsition.min.js"></script>
        <!--===============================================================================================-->
        	<script type="text/javascript" src="resources/vendor/bootstrap/js/popper.js"></script>
        	<script type="text/javascript" src="resources/vendor/bootstrap/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        	<script type="text/javascript" src="resources/vendor/select2/select2.min.js"></script>
        <!--===============================================================================================-->
        	<script type="text/javascript" src="resources/vendor/daterangepicker/moment.min.js"></script>
        	<script type="text/javascript" src="resources/vendor/daterangepicker/daterangepicker.js"></script>
        <!--===============================================================================================-->
        	<script type="text/javascript" src="resources/vendor/countdowntime/countdowntime.js"></script>
        <!--===============================================================================================-->
            <script type="text/javascript" src="https://kit.fontawesome.com/c19e7ec70a.js"></script>
        <!--===============================================================================================-->
        	<script type="text/javascript" src="resources/js/main.js"></script>
	</body>
</html>