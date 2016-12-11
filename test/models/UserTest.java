/**
 * @author Denise Doyle 
 * User test class - testIds not working
 */

package models;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class UserTest {

	User Rob = new User("Rob", "Test", 21, "M", "Student");
	User Ann = new User("Ann", "Test", 22, "F", "Student");

	@Test
	public void testCreate() {
		assertEquals("Rob", Rob.getFirstName());
		assertEquals("Test", Rob.getLastName());
		assertEquals(21, Rob.getAge());
		assertEquals("M", Rob.getGender());
		assertEquals("Student", Rob.getOccupation());
	}

	@Test
	public void testToString() {
		assertEquals("User: ID=" + Rob.getId() + ", First Name = " + Rob.getFirstName() + ", Last Name = "
				+ Rob.getLastName() + ", Age = " + Rob.getAge() + ", Gender = " + Rob.getGender() + ", Occupation = "
				+ Rob.getOccupation() + "]", Rob.toString());
	}

	@Test
	public void testEquals() {
		User Robbie = new User("Rob", "Test", 21, "M", "Student");

		assertEquals(Rob, Rob);
		assertEquals(Rob, Robbie);
		assertNotEquals(Rob, Ann);
	}

	//
	// @Test
	// public void testIds(){
	// Set<Long> id = new HashSet<>();
	// for(User user: users){
	// id.add(user.getId());
	// }
	// assertEquals(user.length, id.size());
	// }

}