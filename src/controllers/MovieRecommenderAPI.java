/**
 * @author Denise Doyle
 * MovieRecommenderAPI class - loads data via CVSLoader (small or large dataset), add/remove/get users
 * add/remove/get movies, add/remove/get ratings, top 10 movies based on ratings, recommendations from users, 
 */
package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Movie;
import models.Rating;
import models.User;
import utils.CSVLoader;
import utils.Serializer;

public class MovieRecommenderAPI implements Recommender {

	private Map<Long, User> userIndex = new HashMap<>();
	private Map<Long, Movie> movieIndex = new HashMap<>();
	// private Map<Long, Rating> ratingIndex = new HashMap<>();
	private Serializer serializer;

	public MovieRecommenderAPI(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public void prime() throws Exception {
		CSVLoader loader = new CSVLoader();
		List<User> users = loader.loadUsers("data/small/users5.dat");
		// List <User> users = loader.loadUsers("data/large/users.dat");
		for (User user : users) {
			addUser(user);
		}
		List<Movie> movies = loader.loadMovies("data/small/items5.dat");
		// List <Movie> movies = loader.loadMovies("data/large/items.dat");
		for (Movie movie : movies) {
			addMovie(movie);
		}
		List<Rating> ratings = loader.loadRatings("data/small/ratings5.dat");
		// List <Rating> ratings = loader.loadRatings("data/large/ratings.dat");
		for (Rating rating : ratings) {
			addRating(rating);
		}
	}

	@Override
	public void addUser(User user) {
		userIndex.put(user.getId(), user);
	}

	@Override
	public void addUser(String firstName, String lastName, int age, String gender, String occupation) {
		User user = new User(firstName, lastName, age, gender, occupation);
		userIndex.put(user.getId(), user);
	}

	@Override
	// Couldn't create a cleaner way to remove ratings when user is removed?
	public void removeUser(Long userID) {
		if (userIndex.containsKey(userID)) {
			userIndex.remove(userID);
			for (Movie movie : movieIndex.values()) {
				for (int i = 0; i < movie.getRatings().size(); i++) {
					if (movie.getRatings().get(i).getUserID() == userID) {
						movie.getRatings().remove(i);
						i--;
					}
				}
			}
		} else {
			System.out.println("Invalid User ID");
		}
	}

	@Override
	public Map<Long, User> getUsers() {
		return userIndex;
	}

	@Override
	public void addMovie(String title, String year, String url) {
		Movie movie = new Movie(title, year, url);
		movieIndex.put(movie.getId(), movie);

	}

	@Override
	public void addMovie(Movie movie) {
		movieIndex.put(movie.getId(), movie);
	}

	@Override
	public void removeMovie(Long movieID) {
		if (movieIndex.containsKey(movieID)) {
			movieIndex.remove(movieID);
			for (User user : userIndex.values()) {
				for (int j = 0; j < user.getRatings().size(); j++) {
					if (user.getRatings().get(j).getMovieID() == movieID) {
						user.getRatings().remove(j);
						j--;
					}
				}
			}
		} else {
			System.out.println("There are no movies with this ID");
		}
	}

	@Override
	public Movie getMovie(Long movieID) {
		return movieIndex.get(movieID);
	}

	@Override
	public Map<Long, Movie> getMovies() {
		return movieIndex;
	}

	@Override
	public void addRating(Long userID, Long movieID, int rating) {
		Rating newRating = new Rating(userID, movieID, rating);
		boolean hasBeenRated = false;
		// if a movie has a rating, rating = true
		if (movieIndex.containsKey(movieID)) {
			for (Map.Entry<Long, Movie> ratings : movieIndex.entrySet()) {
				if (ratings.getKey().equals(movieID)) {
					for (Rating rating1 : ratings.getValue().getRatings()) {
						if (userID.equals(rating1.getUserID())) {
							hasBeenRated = true;
							break;
						}
					}
					// if movie hasn't been rated, add new rating
					if (userIndex.containsKey(userID) && !hasBeenRated) {
						ratings.getValue().addRating(newRating);
					}
					break;
				}
			}
			// if user hasn't rated, add rating
			if (userIndex.containsKey(userID) && !hasBeenRated) {
				for (Map.Entry<Long, User> ratings : userIndex.entrySet()) {
					if (ratings.getKey().equals(userID)) {
						ratings.getValue().addRating(newRating);
					}
				}
			} else if (!userIndex.containsKey(userID)) {
				System.out.println("Invalid user ID");
			} else {
				System.out.println("This movie has already been rated");
			}
		} else {
			System.out.println("Invalid movie ID");
		}

	}

	@Override
	public void addRating(Rating rating) {
		// add for movie
		for (Map.Entry<Long, Movie> ratings : movieIndex.entrySet()) {
			if (ratings.getKey().equals(rating.getMovieID())) {
				ratings.getValue().addRating(rating);
			}
		}
		// add for user
		for (Map.Entry<Long, User> users : userIndex.entrySet()) {
			if (users.getKey().equals(rating.getUserID())) {
				users.getValue().addRating(rating);
			}
		}
	}

	@Override
	// bugged? returns double ratings for some
	public void getRating(Long userID, Long movieID) {
		String r = "";
		for (int i = 0; i < userIndex.get(userID).getRatings().size(); i++) {
			Rating rating = userIndex.get(userID).getRatings().get(i);
			if (movieID.equals(rating.getMovieID())) {
				r += rating.toString();
			}
		}
		if (r.equals("") || r.equals(null)) {
			r = "This user did not rate this movie";
		}
		System.out.println(r);
	}

	@Override
	public List<Rating> getUserRatings(Long userID) {
		if (movieIndex.containsKey(userID)) {
			return userIndex.get(userID).getRatings();
		} else {
			return null;
		}

	}

	@Override
	public List<Rating> getMovieRatings(Long id) {
		if (movieIndex.containsKey(id)) {
			return movieIndex.get(id).getRatings();
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<Rating> getRatings() {
		ArrayList<Rating> ratingIndex = new ArrayList<Rating>();
		for (User user : userIndex.values()) {
			for (Rating rating : user.getRatings()) {
				ratingIndex.add(rating);
			}
		}
		return ratingIndex;
	}

	@Override
	public List<Movie> getUserRecommendations(Long userID) {
		ArrayList<Movie> recommendations = new ArrayList<Movie>();
		User user = userIndex.get(userID);
		if (!getRatings().isEmpty() && !user.getRatings().isEmpty()) {
			User largestRating = user;
			User smallestRating = user;
			int highestRating = Integer.MIN_VALUE;
			User closestUser = user;
			for (User userRating : userIndex.values()) {
				if (!userRating.equals(user)) {
					if (user.getRatings().size() > userRating.getRatings().size()) {
						smallestRating = userRating;
					} else {
						largestRating = userRating;
					}
					int sum = 0;
					boolean sameRating = false;
					// Two for loops, not ideal but works
					for (int i = 0; i < smallestRating.getRatings().size(); i++) {
						for (int j = 0; j < largestRating.getRatings().size(); j++) {
							if (smallestRating.getRatings().get(i).getMovieID() == largestRating.getRatings().get(j)
									.getMovieID()) {
								sum += smallestRating.getRatings().get(i).getRating()
										* largestRating.getRatings().get(j).getRating();
								sameRating = true;
							}
						}
					}
					if (highestRating == Integer.MIN_VALUE && sameRating) {
						highestRating = sum;
						closestUser = userRating;
					} else {
						if (highestRating < sum && sameRating) {
							highestRating = sum;
							closestUser = userRating;
						}
					}
				}
			}
			if (!closestUser.equals(user)) {
				ArrayList<Rating> ratings = new ArrayList<Rating>(closestUser.getRatings());
				Collections.sort(ratings);
				Collections.reverse(ratings);
				for (Rating rating : ratings) {
					boolean hasBeenRated = false;
					for (Rating userRating : user.getRatings()) {
						if (rating.getMovieID().equals(userRating.getMovieID())) {
							hasBeenRated = true;
						}
					}
					if (!hasBeenRated) {
						recommendations.add(getMovie(rating.getMovieID()));
					}
				}

				if (recommendations.size() > 10) {
					return recommendations.subList(0, 10);
				} else if (recommendations.size() == 0) {
					System.out.println("This user has already rated recommendations from a recommended user");
					return null;
				} else {
					return recommendations;
				}
			} else {
				System.out.println("No matching users");
				return null;
			}
		} else if (!getRatings().isEmpty()) {
			System.out.println("There are no ratings to show for this user");
			return null;
		} else {
			System.out.println("Add ratings to get recommendations");
			return null;
		}

	}

	@Override
	public List<Movie> getTopTenMovies() {
		boolean hasRating = false;
		if (!getRatings().isEmpty()) {
			hasRating = true;
		}
		if (hasRating) {
			List<Movie> movies = new ArrayList<Movie>();
			for (Movie movie : getMovies().values()) {
				if (!movie.getRatings().isEmpty()) {
					movies.add(movie);
				}
			}
			Collections.sort(movies);
			Collections.reverse(movies);
			if (movies.size() > 10) {
				return movies.subList(0, 10);
			} else {
				return movies;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load() throws Exception {
		serializer.read();
		movieIndex = (Map<Long, Movie>) serializer.pop();
		userIndex = (Map<Long, User>) serializer.pop();
		// ratingIndex = (Map<Long>, Rating) serializer.pop();
		Movie.counter = (Long) serializer.pop();
		User.counter = (Long) serializer.pop();
	}

	@Override
	public void write() throws Exception {
		serializer.push(User.counter);
		serializer.push(Movie.counter);
		serializer.push(userIndex);
		serializer.push(movieIndex);
		// serializer.push(ratingIndex);
		serializer.write();

	}

}