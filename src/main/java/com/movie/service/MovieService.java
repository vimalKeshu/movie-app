package com.movie.service;

import java.sql.Timestamp;
import java.util.*;

import com.corvesta.keyspring.activitylog.client.ActivityLogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.domain.Movie;
import com.corvesta.keyspring.activitylog.client.ActivityLogInfoService;
import com.corvesta.keyspring.activitylog.client.RestTemplateConfig;

@Service
public class MovieService {
	List<Movie> movieList = new ArrayList<>(Arrays.asList(new Movie(1,"MI6","ACTION",2018),
										  new Movie(2,"MI5","ACTION",2016),
										  new Movie(3,"MI4","ACTION",2012),
										  new Movie(4,"MI3","ACTION",2008)));
	@Autowired
    ActivityLogInfoService activityLogService;
	
	public List<Movie> getAll(final String transactionId, final int healthCarecompanyId, final int subCompanyId){
        this.saveActivityLog("GET ALL MOVIES",transactionId,healthCarecompanyId,subCompanyId);

		return movieList;
	}
	
	public Movie find(final long id, final String transactionId, final int healthCarecompanyId, final int subCompanyId) {
        this.saveActivityLog(id,transactionId,healthCarecompanyId,subCompanyId);
		return (Movie) movieList.stream().filter(movie->movie.getId()==id).findFirst().get();
	}
	
	public void add(Movie movie, final String transactionId, final int healthCarecompanyId, final int subCompanyId) {
		this.saveActivityLog(movie,transactionId,healthCarecompanyId,subCompanyId);
	    this.movieList.add(movie);
	}
	
	public void update(long id, Movie movie, final String transactionId, final int healthCarecompanyId, final int subCompanyId) {
		this.saveActivityLog(movie,transactionId,healthCarecompanyId,subCompanyId);
        for(int i=0;i<movieList.size();i++) {
			if(movieList.get(i).getId() == movie.getId()) {
				movieList.set(i, movie);
				break;
			}
		}		
	}
	
	public void delete(final long id, final String transactionId, final int healthCarecompanyId, final int subCompanyId) {
		this.saveActivityLog(id,transactionId,healthCarecompanyId,subCompanyId);
		movieList.removeIf(movie-> movie.getId()==id);
	}

	private void saveActivityLog(Object object,String transactionId, int healthCarecompanyId, int subCompanyId){
        ActivityLogInfo activityLogInfo = new ActivityLogInfo();
        activityLogInfo.setTransactionId(transactionId); //- Adding transactionId
        activityLogInfo.setEventTimeStamp(new Date()); //- Adding Date
        activityLogInfo.setActivityLogData(object); //- Adding the object that we want to store as a raw Json in the DB

        try {
            activityLogService.createActivityLog(activityLogInfo,healthCarecompanyId,subCompanyId); //- Calling the activity-log Rest API (object to log, healthCareCompanyId, subCompanyId)
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Not able to write activity log.");
        }
    }
}
