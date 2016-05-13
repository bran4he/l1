package com.xueqiu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.test.TestExample;

/**
 * 用于查看html版本特殊需求日志
 * @author bran
 *
 */
@WebServlet(name="logServlet", urlPatterns={"/log"})
public class LogServlet extends HttpServlet {

	private static final long serialVersionUID = -1250763728916090226L;
	private static Logger logger = Logger.getLogger(LogServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		//logger.warn("getting HTML log warn");
		
		res.setHeader("Cache-Control", "no-cache");  
		res.setHeader("Cache-Control", "no-store");  
		res.setHeader("Pragma", "no-cache");  
		res.setDateHeader("Expires", 0);
		
		req.getRequestDispatcher("WEB-INF/demo-log-html.html").forward(req, res);
//		res.sendRedirect("WEB-INF/demo-log-html.html");
	}
	
	
}
