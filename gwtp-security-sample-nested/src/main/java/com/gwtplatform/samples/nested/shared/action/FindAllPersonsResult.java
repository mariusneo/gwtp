package com.gwtplatform.samples.nested.shared.action;

import java.util.List;

import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.samples.nested.shared.domain.Person;

public class FindAllPersonsResult implements Result{
	List<Person> persons;
	
	public FindAllPersonsResult(){}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
