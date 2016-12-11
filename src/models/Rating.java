/**
 * @author Denise Doyle
 * Rating Class - Constructor, getter and setter. UserID and MovieID getters and setters to rate specific movies from a specific user
 */
package models;

public class Rating implements Comparable<Rating> {
	
	private Long userID;
	private Long movieID;
	private int rating;

	public Rating(Long userID, Long movieID, int rating) {
		this.userID = userID;
		this.movieID = movieID;
		this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}
	
	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	@Override
	public int compareTo(Rating that) {
		return Double.compare(this.getRating(), that.getRating());
	}
	
	@Override
	public String toString() {
		return "[Rating: User ID = " + userID + ", Movie ID = " + movieID + ", Rating = " + rating + "]";
	}

}