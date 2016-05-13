package com.xueqiu.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name="getData", urlPatterns="/getData")
public class Getdata extends HttpServlet {

	private static final long serialVersionUID = -8687400820173853188L;
	private static Logger logger = Logger.getLogger(Getdata.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		
		logger.warn("getting info");
		
		String href = req.getParameter("href");
		String cookie = req.getParameter("cookie");
		
		if(null != href){
			System.out.println(href);
			System.out.println(cookie);
		}
		res.getWriter().write("");
	}
       

}
