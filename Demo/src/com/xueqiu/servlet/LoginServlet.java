package com.xueqiu.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


@WebServlet(name="loginServlet", urlPatterns="/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -5836569659734526399L;
	
	private String result ="";
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		System.out.println(req.getParameter("username"));
//		System.out.println(req.getParameter("password"));
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/plain; charset=utf-8");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//本地浏览
		if(username.equalsIgnoreCase("local") && password.equalsIgnoreCase("111")){
			System.out.println(username +"本地加载图片");
			
			//	/home/bran/xueqiu
			Path path = Paths.get("/home", "bran", "xueqiu", "tt");
			
			Files.walkFileTree(path, new SimpleFileVisitor<Path>(){

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if(!attrs.isDirectory()){
//						System.out.println(file.getFileName());
//						System.out.println(file.toFile().getCanonicalPath());
						result = result +file.toFile().getCanonicalPath() +";";
						
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
				
			});
			result = result.substring(0, result.length()-1);
			//res.getWriter().write(result);
			req.setAttribute("result", result);
			req.setAttribute("source", "local");
			req.getRequestDispatcher("/WEB-INF/xueqiu-list.jsp").forward(req, res);
			
		//远程加载图片浏览
		}else if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("111")){
			System.out.println(username +" 远程加载图片");
		}else{
			System.out.println(username +" 登陆失败 " + password);
			res.getWriter().println("<p style=\'color:red;\'>登陆失败，请联系管理员！<p>");
		}
	}
       

}
