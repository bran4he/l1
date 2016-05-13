package com.movie.servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.movie.bean.MovieBean;
import com.movie.bean.WebAnalyser;
import com.xueqiu.servlet.FakeUrl;

@WebServlet(name="moviesUpdate", urlPatterns="/movie")
public class MoviesUpdate extends HttpServlet {
       
	private static final long serialVersionUID = 2034427495459566984L;

	private static Logger logger = Logger.getLogger(MoviesUpdate.class);
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		logger.info("stat analysis the site...");
		
		WebAnalyser wa = new WebAnalyser();
		List<MovieBean> moveList = wa.getMovies();
		
		request.setAttribute("list", moveList);
		
		request.getRequestDispatcher("/WEB-INF/movieList.jsp").forward(request, response);
	}

}
