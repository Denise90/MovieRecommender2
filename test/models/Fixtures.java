/**
 * @author Denise Doyle 
 * Fixtures class for use in tests
 */

package models;

public class Fixtures {

	public static User[] users = { 
			new User("Robert", "Test", 21, "M", "student"),
			new User("Ann", "Test", 22, "F", "student"), 
			new User("Clyde", "Test", 42, "M", "student"),
			new User("Sarah", "Test", 28, "F", "student") };

	public static Movie[] movies = { 
			new Movie("The Shawshank Redemption", "1994", "shawshank.com"),
			new Movie("The Godfather", "1972", "thegodfather.com"),
			new Movie("The Dark Knight", "2008", "thedarkknight.com"),
			new Movie("Pulp Fiction", "1994", "pulpfiction.com"), };

	public static Rating[] ratings = { 
			new Rating(Long.valueOf(0), Long.valueOf(0), 2), 
			new Rating(Long.valueOf(1), Long.valueOf(1), 3),
			new Rating(Long.valueOf(2), Long.valueOf(2), 4),
			new Rating(Long.valueOf(3), Long.valueOf(3), 5), 
	};
}