package com.movie.pojo;

public class Movie {

	private long id;
	private String title;
	private String genre;
	private long year;
	
	public Movie() {}
	
	public Movie(long id,String title,String genre,long year) {
		this.id=id;
		this.title=title;
		this.genre=genre;
		this.year=year;
	}
	
	public long getId() {
		return id;
	}	

	public String getTitle() {
		return title;
	}

	public String getGenre() {
		return genre;
	}

	public long getYear() {
		return year;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Movie)) {
			return false;
		}
		Movie other = (Movie) obj;
		if (id != other.id) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}



		
}
