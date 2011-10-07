package com.gwtplatform.samples.nested.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gwtplatform.samples.nested.shared.domain.Person;

public class PersonFuzzer {
	public static final int MAX_PEOPLE = 100;

	private static final String[] FIRST_NAMES = new String[] { "Inman",
			"Sally", "Omar", "Teddy", "Jimmy", "Cathy", "Barney", "Fred",
			"Eddie", "Carlos" };

	private static final String[] LAST_NAMES = new String[] { "Smith", "Jones",
			"Epps", "Gibbs", "Webber", "Blum", "Mendez", "Crutcher", "Needler",
			"Wilson", "Chase", "Edelstein" };

	public static List<Person> generateRandomPeople() {
		List<Person> toReturn = new ArrayList<Person>(MAX_PEOPLE);
		Random rnd = new Random(3);
		for (long index = 0; index < MAX_PEOPLE; index++) {
			Person person = generateRandomPerson(rnd);
			person.setId(index);
			toReturn.add(person);
		}
		return toReturn;
	}

	private static Person generateRandomPerson(Random rnd) {
		Person student = new Person();

		String firstName = pickRandomString(rnd, FIRST_NAMES);
		String lastName = pickRandomString(rnd, LAST_NAMES);
		student.setFirstName(firstName);
		student.setLastName(lastName);

		return student;
	}

	private static String pickRandomString(Random rnd, String[] a) {
		int i = rnd.nextInt(a.length);
		return a[i];
	}
}
