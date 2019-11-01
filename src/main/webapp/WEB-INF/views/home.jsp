<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <title>File Share | Home</title>
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
  		
    	<!-- add folder button -->
		<button type="button" style="float: right; padding: 5px;" onclick="addFolderModal()">
			<img src="resources/images/icons/add-folder.svg"
				class="add-folder-icon">
		</button>
		
		<!-- shared with me folder -->
		<a><img src="resources/images/icons/folder-10.svg"></a>
		
		<!-- modal -->
		<div id="addFolderModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
	
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
					<form action="./addfolder" method="get" enctype='multipart/form-data'>
						<input type="text" name="newfolder" class="newfolder" id="newfolder" placeholder="folder name">
						<button class="add-folder-btn" onclick="addFolder()">Add</button>
					</form>
				</div>
				</div>
	
			</div>
		</div>


		<!-- rename folder modal -->
		<div id="renameFolderModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
	
				<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body">
					<form action="./renamefolder" method="get">
						<input type="text" name="renamefolder" class="renamefolder"
							id="renamefolder" placeholder="foldername"> <input
							type="hidden" class="folderid" id="folderid" name="folderid">
						<button type="button" class="rename-folder-btn"
							onclick="renameFolder()">Rename</button>
					</form>
				</div>
			</div>

		</div>
		</div>
		
		<!-- delete folder modal -->
		<div id="deleteFolderModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
	
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h5 style="padding: 10px;">Confirm delete?</h5>
					</div>
					<div class="modal-body" id="delete-modal-body">
						<form action="./deletefolder" method="get">
						<input type="hidden" class="deletefolderid" id="deletefolderid" name="deletefolderid">
							<button type="button" class="delete-folder-btn" onclick="deleteFolder()">Delete</button>
						</form>
					</div>
				</div>
	
			</div>
		</div>
	
		<!-- grid -->
		<div class="container">
			<div class="row">
				<div class="folder-blocks">
					<c:forEach var="i" items="${folders}">
						<div class="dropdown">
							<a href="./folder?folderid=${i.getFolderid()}"> <img class="folder-icon"
							src="resources/images/icons/close-folder.svg"><br>${i.getFolder()}<br>
							</a>
							<div class="dropdown-content">
								<button class="rename-folder" onclick="renameFolderModal(${i.getFolderid()})"><img src="resources/images/icons/rename.svg" class="rename-folder-icon"></button>
								<button class="delete-folder" onclick="deleteFolderModal(${i.getFolderid()})"><img src="resources/images/icons/delete.svg" class="delete-folder-icon"></button>	
							</div>
						</div>
					</c:forEach>
				</div>	
			</div>
		</div>

	<%@ include file="footer.jsp" %>
		<!--===============================================================================================-->
        	<script src="resources/vendor/jquery/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        	<script src="resources/vendor/animsition/js/animsition.min.js"></script>
        <!--===============================================================================================-->
        	<script src="resources/vendor/bootstrap/js/popper.js"></script>
        	<script src="resources/vendor/bootstrap/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        	<script src="resources/vendor/select2/select2.min.js"></script>
        <!--===============================================================================================-->
        	<script src="resources/vendor/daterangepicker/moment.min.js"></script>
        	<script src="resources/vendor/daterangepicker/daterangepicker.js"></script>
        <!--===============================================================================================-->
        	<script src="resources/vendor/countdowntime/countdowntime.js"></script>
        <!--===============================================================================================-->
            <script src="https://kit.fontawesome.com/c19e7ec70a.js"></script>
        <!--===============================================================================================-->
        	<script src="resources/js/main.js"></script>
    </body>
 </html>