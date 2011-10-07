package com.gwtplatform.samples.nested.shared.domain;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -7525480494551366430L;

	private long id;
	private String firstName;
	private String lastName;

	public Person() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

}
