package com.gwtplatform.samples.nested.server.dao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.gwtplatform.samples.nested.server.PersonFuzzer;
import com.gwtplatform.samples.nested.shared.domain.Person;

/**
 * Naive implementation for  <code>Person</code> DAO object used to provide CRUD operations
 * for a collection of persons.
 * 
 * @author mga
 *
 */
public class PersonDao {
	private static final List<Person> persons = PersonFuzzer
			.generateRandomPeople();

	public PersonDao() {
	}

	public Person add(Person person) {
		person.setId(persons.size());
		persons.add(person);
		return person;
	}

	public Person findPerson(long id) {
		for (Person person : persons) {
			if (person.getId() == id) {
				return person;
			}
		}
		return null;
	}
	
	public List<Person> findPersons(String lastName){
		List<Person> result = new LinkedList<Person>();
		for (Person person : persons) {
			if (person.getLastName().equals(lastName)) {
				result.add(person);
			}
		}
		return result;
	}

	public List<Person> findAllPersons() {
		return persons;
	}

	public boolean deletePerson(long id) {
		for (Iterator<Person> iterator = persons.iterator(); iterator.hasNext();) {
			Person person = iterator.next();
			if (person.getId() == id) {
				iterator.remove();
				return true;
			}
		}

		return false;
	}
}
