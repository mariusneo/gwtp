package com.gwtplatform.samples.nested.shared;

public class Permissions {
	public static final int ACCESS_ADMINISTRATION_PAGE = 1;

	public static final int SEARCH_PERSON_WITH_GIVEN_NAME = 2;

	public static final int SEARCH_ALL_PERSONS = 3;

	public static int getAccessAdministrationPage() {
		return ACCESS_ADMINISTRATION_PAGE;
	}

	public static int getSearchPersonWithGivenName() {
		return SEARCH_PERSON_WITH_GIVEN_NAME;
	}

	public static int getSearchAllPersons() {
		return SEARCH_ALL_PERSONS;
	}

}
