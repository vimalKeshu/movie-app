package com.movie.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.movie.pojo.Movie;
import com.movie.service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(method=RequestMethod.GET,value="/list")
	@PreAuthorize("hasRole('ROLE_corvesta-user')")
	public List<Movie> getAll(){
		return movieService.getAll();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@PreAuthorize("hasRole('ROLE_corvesta-user')")
	public Movie get(@PathVariable long id) {
		return movieService.find(id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	@PreAuthorize("hasRole('ROLE_corvesta-admin')")
	public void create(@RequestBody Movie movie) {
		movieService.add(movie);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/update/{id}")
	@PreAuthorize("hasRole('ROLE_corvesta-admin')")
	public void update(@PathVariable long id,@RequestBody Movie movie) {
		movieService.update(id, movie);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/delete/{id}")
	@PreAuthorize("hasRole('ROLE_corvesta-admin')")
	public void delete(@PathVariable long id) {
		movieService.delete(id);
	}	
	
}
