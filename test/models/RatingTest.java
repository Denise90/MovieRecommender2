/**
 * @author Denise Doyle 
 * Rating test class - error with testCreate()
 */

package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class RatingTest {

	Rating rating1 = new Rating(Long.valueOf(0), Long.valueOf(0), 2);
	Rating rating2 = new Rating(Long.valueOf(1), Long.valueOf(1), 3);
	Rating rating3 = new Rating(Long.valueOf(2), Long.valueOf(2), 4);

	@Test
	public void testCreate() {
		assertEquals(rating1.getUserID(), 0, 2);
		assertNotEquals(rating2.getUserID(), 2, 3); //Values should be different error.
		assertEquals(0, rating1.getMovieID(), 2);
		assertNotEquals(1, rating1.getMovieID(), 1);
		assertNotEquals(1, 5, rating1.getRating());
	}

	@Test
	public void testEquals() {
		assertEquals(rating1, rating1);
		assertEquals(rating2, rating2);
		assertNotEquals(rating1, rating2);
	}
	
	@Test
	public void testToString() {
		assertEquals("[Rating: User ID = " + rating1.getUserID() + ", Movie ID = " + rating1.getMovieID()
				+ ", Rating = " + rating1.getRating() + "]", rating1.toString());
	}

}