package com.xueqiu.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name="getInfo", urlPatterns="/getInfo")
public class GetInfo extends HttpServlet {

	private static final long serialVersionUID = -3909915978425081461L;
	
	private static Logger logger = Logger.getLogger(GetInfo.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		
		logger.warn("getting info");
        // 记录debug级别的信息  
        //logger.debug("This is debug message.");  
        // 记录info级别的信息  
        //logger.info("This is info message.");  
        // 记录error级别的信息  
        //logger.error("This is error message."); 
		
		//req.setCharacterEncoding("UTF-8");    
		
		String ip = req.getRemoteAddr();
		String host = req.getRemoteHost();
		System.out.println("ip:" + ip);
		System.out.println("host:"+ host);
		
		String scriptData = req.getParameter("scriptData");
		
		
		if(null != scriptData){
			System.out.println(scriptData);
			String temp = scriptData.substring(scriptData.indexOf("return"), scriptData.lastIndexOf("art.dialog"));
			int int1 = Integer.parseInt(temp.substring(temp.indexOf("(")+1, temp.indexOf(">=")).trim());
			int int2 = Integer.parseInt(temp.substring(temp.indexOf(">=")+2, temp.indexOf(")")).trim());
			System.out.println("int1:" +int1+", int2:" + int2);
			if(int1 < int2){
				res.getWriter().write("good luck, you can login now!");
			}else{
				res.getWriter().write("sorry, refresh again!");
			}
		}
		
		res.getWriter().write("");
	}
       

}
