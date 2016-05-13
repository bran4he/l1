package com.xueqiu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name="fakeUrl", urlPatterns="/12308/safeteam/verifyuser")
public class FakeUrl extends HttpServlet {

	private static final long serialVersionUID = 8319413355379882134L;
	private static Logger logger = Logger.getLogger(FakeUrl.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		logger.warn("someone is connect to me");
		req.setCharacterEncoding("UTF-8"); 
		req.getRequestDispatcher("/12308.jsp").forward(req, res);
	}
       
	
}
