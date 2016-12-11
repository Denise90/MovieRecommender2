/**
 * @author Denise Doyle 
 * User class - user constructors x2. Getters and setters for user attributes. Add, get and set ratings for user.
 */

package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

	public static Long counter = 0l;
	private Long id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String occupation;
	// User has an array list of type ratings (to add ratings for a specific user id)
	private List<Rating> ratings = new ArrayList<>();


	// Needed for user constructor in Main
	public User(Long id, String firstName, String lastName, int age, String gender, String occupation) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
	}

	// Needed for user constructor in MovieRecommenderAPI
	public User(String firstName, String lastName, int age, String gender, String occupation) {
		counter++;
		this.id = counter;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.occupation = occupation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String secondName) {
		this.lastName = secondName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public void addRating(Rating newRating) {
		ratings.add(newRating);
	}
	
	public List<Rating> getRatings() {
		return ratings;
	}
	
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof User) {
			final User other = (User) obj;
			return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
					&& Objects.equals(gender, other.gender) && Objects.equals(age, other.age)
					&& Objects.equals(occupation, other.occupation) && Objects.equals(ratings, other.ratings);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "[User: ID=" + id + ", First Name = " + firstName + ", Last Name = " + lastName + ", Age = " + age
				+ ", Gender = " + gender + ", Occupation = " + occupation + "]";
	}

}