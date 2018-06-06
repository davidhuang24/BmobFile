package com.dh.exam.bmobfiledemo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;


public class Movie extends BmobObject {

	private static final long serialVersionUID = 1L;
	private String movieName;
	private BmobFile movie;

	public Movie(){
		
	}
	
	public Movie(String movieName,BmobFile movie){
		this.movieName =movieName;
		this.movie = movie;
	}
	
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public BmobFile getMovie() {
		return movie;
	}

	public void setMovie(BmobFile movie) {
		this.movie = movie;
	}
	
	
	
}
