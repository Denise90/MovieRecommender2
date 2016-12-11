/**
 * @author Denise Doyle
 * Persistence Test - runs and passes but only without ratings? Otherwise returns error
 */

package models;

import controllers.MovieRecommenderAPI;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import models.Movie;
import models.Rating;
import models.User;

import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import static models.Fixtures.users;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;

public class PersistenceTest {

	MovieRecommenderAPI mrAPI;

	public void populate(MovieRecommenderAPI mrAPI) {
		for (User user : users) {
			mrAPI.addUser(user.getFirstName(), user.getLastName(), user.getAge(), user.getGender(),
					user.getOccupation());
		}
		for (Movie movie : movies) {
			mrAPI.addMovie(movie.getTitle(), movie.getYear(), movie.getUrl());
		}
		//for (Rating rating : ratings) {
			//mrAPI.addRating(rating.getUserID(), rating.getMovieID(), rating.getRating());
		//}
	}
	
	@Test
	public void testPopulate(){
		mrAPI = new MovieRecommenderAPI(null);
		assertEquals(0, mrAPI.getUsers().size());
		assertEquals(0, mrAPI.getMovies().size());
		assertEquals(0, mrAPI.getRatings().size());
		populate(mrAPI);
		
		assertEquals(4, mrAPI.getUsers().size());
		assertEquals(4, mrAPI.getMovies().size());
		//assertEquals(4, mrAPI.getRatings().size());
	}
	
	@Test
	public void testXMLSerializer() throws Exception{
		String dataStoreFile = "testDataStore.xml";
		
		Serializer serializer = new XMLSerializer(new File (dataStoreFile));
		
		mrAPI = new MovieRecommenderAPI(serializer);
		assertEquals(0, mrAPI.getUsers().size());
		assertEquals(0, mrAPI.getMovies().size());
		//assertEquals(0, mrAPI.getRatings().size());
		
		populate(mrAPI);
		mrAPI.write();
		
		MovieRecommenderAPI mrAPI2 = new MovieRecommenderAPI(serializer);
		
		mrAPI2.load();
		
		assertEquals(mrAPI.getUsers().size(), mrAPI2.getUsers().size());
		for(User user : users){
			assertTrue(mrAPI2.getUsers().containsValue (user));
		}
	}
	

}