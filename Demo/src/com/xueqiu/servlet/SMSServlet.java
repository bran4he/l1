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

import com.example.test.PostTest;


@WebServlet(name="sMSServlet", urlPatterns="/sms")
public class SMSServlet extends HttpServlet {
	private static final long serialVersionUID = 4638175772059075013L;
	
	private static Logger logger = Logger.getLogger(SMSServlet.class);
	
	private String result ="";
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("come in...");
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/plain; charset=utf-8");
		
		String phone = req.getParameter("phone");
		String times = req.getParameter("times");
		
		System.out.println(phone+","+times);
		
		for(int i= 1; i<= Integer.valueOf(times); i++){
			int n = i % 6 + 1;
			
			switch (n) {
			case 1:
				logger.debug("开始发送第"+i+"条");
				PostTest.send1(phone);
				break;
			case 2:
				logger.debug("开始发送第"+i+"条");
				PostTest.send2(phone);
				break;
			case 3:
				logger.debug("开始发送第"+i+"条");
				PostTest.send3(phone);
				break;
			case 4:
				logger.debug("开始发送第"+i+"条");
				PostTest.send4(phone);
				break;
			case 5:
				logger.debug("开始发送第"+i+"条");
				PostTest.send5(phone);
				break;
			case 6:
				logger.debug("开始发送第"+i+"条");
				PostTest.send6(phone);
				break;
			default:
				break;
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("发送第"+i+"条成功！");
		}
		
//		res.sendRedirect("/Demo/boom.jsp");
		res.getWriter().write("发送成功");
	}
       

}
