package com.movie.bean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.movie.bean.MovieBean;

public class WebAnalyser {

	private static final String WEB_SITE= "http://www.mp4ba.com/index.php?sort_id=3";
	
	private static Logger logger = Logger.getLogger(WebAnalyser.class);
	
	public List<MovieBean> getMovies(){
		List<MovieBean> moveList =new ArrayList<MovieBean>();
		try {
			Document doc = Jsoup.connect(WEB_SITE).timeout(4000).get();
			Elements trs = doc.select("#data_list a[href^=show.php]");
			for(int i=0; i< trs.size(); i++){
				String name = trs.get(i).html();
				String url = trs.get(i).absUrl("href");
				MovieBean mb = new MovieBean(url, name);
				moveList.add(mb);
			}
			
			return moveList;
		} catch (IOException e) {
			logger.error("analysis the website error");
			e.printStackTrace();
		}
		return null;
	}


//	public static void main(String[] args) {
//		List<MovieBean> moveList = new ArrayList<MovieBean>();
//		
//		try {
//			Document doc = Jsoup.connect(WEB_SITE).timeout(4000).get();
//			Elements trs = doc.select("#data_list a[href^=show.php]");
//			for(int i=0; i< trs.size(); i++){
//				String name = trs.get(i).html();
//				String url = trs.get(i).absUrl("href");
//				MovieBean mb = new MovieBean(url, name);
//				moveList.add(mb);
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}

}
