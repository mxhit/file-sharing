package com.daddyscode.fileupload;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.daddyscode.dao.FileSharingDao;
import com.daddyscode.dto.Files;
import com.daddyscode.dto.Folder;
import com.daddyscode.dto.SharedWithMe;
import com.daddyscode.dto.Users;

@Controller
public class FileUploadController {
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam("fullname") String fullname, @RequestParam("email") String email,
			@RequestParam("username") String username, @RequestParam("password") String password) {
		FileSharingDao fileShare = new FileSharingDao();

		try {
			fileShare.registerUser(username, password, fullname, email);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "redirect:/login";
	}
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "/login";
		} else {
			return "redirect:/home";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model,@RequestParam Map<String, String> data) {
		HttpSession session = request.getSession();
		
		String username;
		int userid = 0;
		
		Users login = new Users();
		login.setUsername(data.get("username"));
		login.setPassword(data.get("password"));
		
		username = data.get("username");
		session.setAttribute("user", data.get("username"));
		
		FileSharingDao fileSharing = new FileSharingDao();
		boolean authenticated = false;
		try {
			authenticated = fileSharing.getAuthentication(login);
			userid = fileSharing.getUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (userid != 0 && authenticated == true) {
			session.setAttribute("userid", userid);
			
			return "redirect:/home";
		} else {
			session.setAttribute("username", null);
			
			return "/login";
		}
	}
	
	@RequestMapping("/home")
	public String home(HttpServletRequest request) {
		int userid = (Integer) request.getSession().getAttribute("userid");
		FileSharingDao fileShare = new FileSharingDao();
		try {
			ArrayList<Folder> folders = fileShare.getFolder(userid);
			
			request.setAttribute("folders", folders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	
	@RequestMapping("/folder")
	public ModelAndView folder(HttpServletRequest request) {
		int folderid = Integer.parseInt(request.getParameter("folderid"));
		int userid = (Integer) request.getSession().getAttribute("userid");
		request.getSession().setAttribute("folderid", folderid);
		
		ArrayList<Files> files;
		ArrayList<Users> users;
		
		FileSharingDao fileShare = new FileSharingDao();
		try {
			files = fileShare.getFiles(folderid, userid);
			users = fileShare.getUsers();
			
			request.setAttribute("files", files);
			request.setAttribute("users", users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("folder");
	}
	
	@RequestMapping("/shared")
	public ModelAndView shared(HttpServletRequest request) {
//		int folderid = Integer.parseInt(request.getParameter("folderid"));
		int userid = (Integer) request.getSession().getAttribute("userid");
//		request.getSession().setAttribute("folderid", folderid);
		
//		String permission = (String) request.getSession().getAttribute("permission");
//		System.out.println("Permission: " + permission);
		
		ArrayList<SharedWithMe> shared;
		
		FileSharingDao fileShare = new FileSharingDao();
		try {
			shared = fileShare.getSharedFiles(userid);
			
			request.getSession().setAttribute("shared", shared);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("shared");
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		request.getSession().invalidate();
		
		return "redirect:/login";
	}
	
	/*==================================================================
	[FOLDER OPERATIONS]*/	
	
	@RequestMapping(value = "/addfolder", method = RequestMethod.GET)
	public String addFolder(HttpServletRequest request, @RequestParam("newfolder") String newfolder) {
		Instant instant = Instant.now();
		String foldername = String.valueOf(instant.toEpochMilli()) + newfolder;
		String createddate = foldername.substring(0, 13);
		String folder = foldername.substring(13);
		int userid = (Integer) request.getSession().getAttribute("userid");
		
		FileSharingDao fileShare = new FileSharingDao();
		try {
			fileShare.addFolder(foldername, createddate, folder, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/renamefolder", method = RequestMethod.GET)
	public String renameFolder(HttpServletRequest request, @RequestParam("renamefolder") String newname) {
		int folderid =  Integer.parseInt(request.getParameter("folderid"));
		
		FileSharingDao fileShare = new FileSharingDao();
		try {
			fileShare.renameFolder(folderid, newname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/deletefolder", method = RequestMethod.GET)
	public String deleteFolder(HttpServletRequest request) {
		int folderid =  Integer.parseInt(request.getParameter("deletefolderid"));
		
		FileSharingDao fileShare = new FileSharingDao();
		try {
			fileShare.deleteFolder(folderid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/home";
	}
	
	/*==================================================================
	[FILE OPERATIONS]*/
	
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public ModelAndView fileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		final String UPLOAD_DIRECTORY = "D:\\Java\\eclipse-workspace\\file-share\\src\\main\\webapp\\resources\\files\\";
		
		int folderid = (Integer) request.getSession().getAttribute("folderid");
		int userid = (Integer) request.getSession().getAttribute("userid");
		
		String filename = String.valueOf(Instant.now().toEpochMilli()) + file.getOriginalFilename();
		
		FileSharingDao fileShare = new FileSharingDao();
		try {
			fileShare.addFiles(filename, folderid, userid);
			
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_DIRECTORY + filename);
			java.nio.file.Files.write(path, bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/folder?folderid=" + folderid);
	}
	
	@RequestMapping(value = "renamefile")
	public ModelAndView renameFile(HttpServletRequest request, @RequestParam("renamefile") String renamefile) {
		int folderid = 0;
		
		try {
			folderid = (Integer) request.getSession().getAttribute("folderid");
			
			int fileid = Integer.parseInt(request.getParameter("renamefileid"));
			
			FileSharingDao fileShare = new FileSharingDao();
			fileShare.renameFile(renamefile, fileid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/folder?folderid=" + folderid);
	}
	
	@RequestMapping(value = "deletefile")
	public ModelAndView deleteFile(HttpServletRequest request) {
		int folderid = 0;
		
		try {
			folderid = (Integer) request.getSession().getAttribute("folderid");
			int fileid = Integer.parseInt(request.getParameter("deletefileid"));
			
			FileSharingDao fileShare = new FileSharingDao();
			fileShare.deleteFile(fileid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/folder?folderid=" + folderid);
	}
	
	@RequestMapping(value = "sharefile", method = RequestMethod.POST)
	public ModelAndView shareFile(HttpServletRequest request, @RequestParam("sharedusername") String sharedusername, @RequestParam("permission") String permission) {
		int folderid = 0;
		
		int fileid = Integer.parseInt(request.getParameter("sharefileid"));
		
		folderid = (Integer) request.getSession().getAttribute("folderid");
		
		System.out.println("shareduserid: " + sharedusername);
		System.out.println("Permission is " + permission);
//		request.getSession().setAttribute("permission", permission);
		
		FileSharingDao fileShare = new FileSharingDao();
		try {
			fileShare.shareFile(fileid, sharedusername, permission);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/folder?folderid=" + folderid);
	}
}