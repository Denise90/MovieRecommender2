package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import models.Movie;
import models.Rating;
import models.User;

public class CSVLoader {

	

public List<User>  loadUsers(String fileName) throws Exception{
	List<User> users=new ArrayList<User>();
	File usersFile = new File(fileName);
    In inUsers = new In(usersFile);
      //each field is separated(delimited) by a '|'
    String delims = "[|]";
    while (!inUsers.isEmpty()) {
    	
        // get user and rating from data source
        String userDetails = inUsers.readLine();

        // parse user details string
        String[] userTokens = userDetails.split(delims);

        // output user data to console.
        if (userTokens.length == 7) {
        users.add(new User(Long.parseLong(userTokens[0]),userTokens[1],userTokens[2],Integer.parseInt(userTokens[3]),userTokens[4],userTokens[5]));
        }
       else
        {
            throw new Exception("Invalid member length: "+userTokens.length);
        }
}
    if (users.size()==0){
    	System.out.println("There were no users in the data file");
    }
    User.counter=(long) users.size();
    return users;
}



public List<Movie> loadMovies(String fileName) throws Exception{
	List<Movie> movies=new ArrayList<Movie>();
	File moviesFile = new File(fileName);
    In inMovies = new In(moviesFile);
      //each field is separated(delimited) by a '|'
    String delims = "[|]";
    while (!inMovies.isEmpty()) {
    	
        // get movie and rating from data source
        String movieDetails = inMovies.readLine();

        // parse movie details string
        String[] movieTokens = movieDetails.split(delims);

        // output user data to console.
        if (movieTokens.length == 23) {
            movies.add(new Movie(Long.parseLong(movieTokens[0]),movieTokens[1],movieTokens[2],movieTokens[3]));
        }else
        {
            throw new Exception("Invalid member length: "+movieTokens.length);
        }
}
    if (movies.size()==0){
    	System.out.println("There were no movies in the data file");
    }
    Movie.counter=(long) movies.size();
    return movies;
}


public List<Rating> loadRatings(String fileName) throws Exception{
	List<Rating> ratings=new ArrayList<Rating>();
	File ratingsFile = new File(fileName);
	In inRatings = new In(ratingsFile);
	//each field is separated(delimited) by a '|'
	String delims = "[|]";
	while (!inRatings.isEmpty()) {

		// get rating and rating from data source
		String ratingDetails = inRatings.readLine();

		// parse rating details string
		String[] ratingTokens = ratingDetails.split(delims);

		// output user data to console.
		if (ratingTokens.length == 4) {
			ratings.add(new Rating(Long.parseLong(ratingTokens[0]),Long.parseLong(ratingTokens[1]),Integer.parseInt(ratingTokens[2])));
		}else
		{
			throw new Exception("Invalid member length: "+ratingTokens.length);
		}
	}
	 if (ratings.size()==0){
	    	System.out.println("There were no ratings in the data file");
	    }
	    return ratings;
}

}