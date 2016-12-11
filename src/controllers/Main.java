/**
 * @author Denise Doyle
 * Main class - Driver for the API using cliche library 
 */
package controllers;

import java.io.File;
import java.util.List;
import java.util.Map;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Movie;
import models.Rating;
import models.User;
import utils.CSVLoader;
import utils.Serializer;
import utils.XMLSerializer;

public class Main {

	public MovieRecommenderAPI mrAPI;

	public Main() throws Exception {
		File datastore = new File("dataStore.xml");
		Serializer serializer = new XMLSerializer(datastore);

		mrAPI = new MovieRecommenderAPI(serializer);
		if (datastore.isFile()) {
			mrAPI.load();
		} else
			mrAPI.prime();
	}

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		Shell shell = ShellFactory.createConsoleShell("Eflix",
				"*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*\n       Welcome to Eflix\nAn Eclipse run movie recommender\n*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*\n   Type ?help for instructions \n --------------------------------",
				main);
		shell.commandLoop();
		main.mrAPI.write();
	}

	@Command(description = "Load data")
	public String load() throws Exception {
		mrAPI.load();
		return "Data Loaded";
	}

	@Command(description = "Save data")
	public String save() throws Exception {
		mrAPI.write();
		return "Data Saved";
	}

	@Command(description = "Add a New User")
	public void addUser(@Param(name = "First Name") String firstName, @Param(name = "Last Name") String lastName,
			@Param(name = "Age") int age, @Param(name = "Gender") String gender,
			@Param(name = "Occupation") String occupation) {
		mrAPI.addUser(firstName, lastName, age, gender, occupation);
	}

	@Command(description = "Remove a User")
	public String removeUser(@Param(name = "id") Long id) {
		mrAPI.removeUser(id);
		return "User has been removed";
	}

	@Command(description = "List All Users")
	public void listUsers() {
		Map<Long, User> users = mrAPI.getUsers();
		if (users.isEmpty()) {
			System.out.println("No users to show");
		} else {
			for (Map.Entry<Long, User> entry : mrAPI.getUsers().entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}
	}

	@Command(description = "List Recommendations for a User")
	public void listUserRecommendations(@Param(name = "id") Long id) {

		if (mrAPI.getUserRecommendations(id) != null) {
			for (Movie entry : mrAPI.getUserRecommendations(id)) {
				System.out.println(entry.toString());
			}
		}
	}

	@Command(description = "Add a Movie")
	public String addMovie(@Param(name = "title") String title, @Param(name = "year") String year,
			@Param(name = "url") String url) {
		mrAPI.addMovie(title, year, url);
		return title + " has been added"; 
	}

	@Command(description = "Remove a Movie")
	public String removeMovie(@Param(name = "id") Long id) {
		mrAPI.removeMovie(id);
		return "Movie has been removed";
	}

	@Command(description = "List All Movies")
	public void listMovies() {
		Map<Long, Movie> movies = mrAPI.getMovies();
		if (movies.isEmpty()) {
			System.out.println("There are no movies yet");
		} else {
			for (Map.Entry<Long, Movie> entry : movies.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}
	}

	@Command(description = "List Top Ten Movies")
	public void listTopTenMovies() {
		List<Movie> topMovies = mrAPI.getTopTenMovies();
		if (topMovies != null) {
			for (Movie entry : topMovies) {
				System.out.println(entry.toString());
			}
		} else {
			System.out.println("No top ten movies to show");
		}

	}

	@Command(description = "List Ratings")
	public void listRatings() {
		List<Rating> ratings = mrAPI.getRatings();
		if (ratings.size() == 0) {
			System.out.println("No ratings to show");
		} else {
			for (Rating entry : ratings) {
				System.out.println(entry.toString());
			}
		}
	}

	@Command(description = "Add a Rating for a Movie")
	public String addRating(@Param(name = "userID") Long userID, @Param(name = "movieID") Long movieID,
			@Param(name = "rating") int rating) {
		mrAPI.addRating(userID, movieID, rating);
		return "Rating added";
	}

	@Command(description = "List Ratings for a User")
	public void listUserRatings(@Param(name = "id") Long id) {
		List<Rating> ratings = mrAPI.getUserRatings(id);
		if (ratings == null) {
			System.out.println("There are no users with this ID");
		} else if (ratings.size() == 0) {
			System.out.println("There are no ratings for this user");
		} else {
			for (Rating rating : ratings) {
				System.out.println(rating.toString());
			}
		}
	}

	@Command(description = "Get a Specific User's Rating for a Particular Movie")
	public void getRating(@Param(name = "userID") Long userID, @Param(name = "movieID") Long movieID) {
		mrAPI.getRating(userID, movieID);
	}

	@Command(description = "List Ratings for a Specific Movie")
	public void listMovieRatings(@Param(name = "id") Long id) {
		List<Rating> ratings = mrAPI.getMovieRatings(id);
		if ((ratings == null) || (ratings.size() == 0)) {
			System.out.println("There are no ratings for this movie");
		} else {
			for (Rating rating : ratings) {
				System.out.println(rating.toString());
			}
		}

	}
}
