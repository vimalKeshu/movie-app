package com.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.movie.domain.Movie;

@Service
public class MovieService {
	List<Movie> movieList = new ArrayList<>(Arrays.asList(new Movie(1,"MI6","ACTION",2018),
										  new Movie(2,"MI5","ACTION",2016),
										  new Movie(3,"MI4","ACTION",2012),
										  new Movie(4,"MI3","ACTION",2008)));
	
	public List<Movie> getAll(){
		return movieList;
	}
	
	public Movie find(final long id) {
		return (Movie) movieList.stream().filter(movie->movie.getId()==id).findFirst().get();
	}
	
	public void add(Movie movie) {
		this.movieList.add(movie);
	}
	
	public void update(long id, Movie movie) {
		for(int i=0;i<movieList.size();i++) {
			if(movieList.get(i).getId() == movie.getId()) {
				movieList.set(i, movie);
				break;
			}
		}		
	}
	
	public void delete(final long id) {
		movieList.removeIf(movie-> movie.getId()==id);
	}
}
