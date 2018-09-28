package com.movie.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import com.movie.domain.Movie;
import com.movie.service.MovieService;

@RestController
@RequestMapping("/movie")
@CrossOrigin
public class MovieController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);
	@Autowired
	private MovieService movieService;

	@RequestMapping(method=RequestMethod.GET)
    //@CrossOrigin(origins = "http://localhost:4242")
	public List<Movie> getAll(@RequestHeader("transactionId") String transactionId,
                              @RequestHeader("healthCarecompanyId") Integer healthCarecompanyId,
                              @RequestHeader("subCompanyId") Integer subCompanyId){
		return movieService.getAll(transactionId,healthCarecompanyId,subCompanyId);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	public Movie get(@PathVariable long id,
                     @RequestHeader("transactionId") String transactionId,
                     @RequestHeader("healthCarecompanyId") Integer healthCarecompanyId,
                     @RequestHeader("subCompanyId") Integer subCompanyId) {
		return movieService.find(id,transactionId,healthCarecompanyId,subCompanyId);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void create(@RequestBody Movie movie,
                       @RequestHeader("transactionId") String transactionId,
                       @RequestHeader("healthCarecompanyId") Integer healthCarecompanyId,
                       @RequestHeader("subCompanyId") Integer subCompanyId) {
		movieService.add(movie,transactionId,healthCarecompanyId,subCompanyId);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	public void update(@PathVariable long id,@RequestBody Movie movie,
                       @RequestHeader("transactionId") String transactionId,
                       @RequestHeader("healthCarecompanyId") Integer healthCarecompanyId,
                       @RequestHeader("subCompanyId") Integer subCompanyId) {
		movieService.update(id, movie,transactionId,healthCarecompanyId,subCompanyId);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	public void delete(@PathVariable long id,
                       @RequestHeader("transactionId") String transactionId,
                       @RequestHeader("healthCarecompanyId") Integer healthCarecompanyId,
                       @RequestHeader("subCompanyId") Integer subCompanyId) {
		movieService.delete(id,transactionId,healthCarecompanyId,subCompanyId);
	}	
	
}
