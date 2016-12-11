/**
 * @author Denise Doyle 
 * Recommender Interface
 */
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import models.*;

public interface Recommender {
	void load() throws Exception;
	void write() throws Exception;
	void addUser(String firstName,String lastName,int age,String gender,String occupation);
	void removeUser(Long userID);
	void addMovie(String title, String year,String url);
	void addRating(Long userID, Long movieID, int rating);
	void getRating(Long userID, Long movieID);
	void addUser(User user);
	void addMovie(Movie movie);
	void addRating(Rating rating);
	void prime() throws Exception;
	void removeMovie(Long movieID);
	Movie getMovie(Long movieID);
	List<Rating> getUserRatings(Long userID);
	List<Movie> getUserRecommendations(Long userID);
	Map<Long, User> getUsers();
	Map<Long, Movie> getMovies();
	ArrayList<Rating> getRatings();
	List<Rating> getMovieRatings(Long id);
	List<Movie> getTopTenMovies();

}