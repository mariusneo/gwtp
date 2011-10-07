package com.gwtplatform.samples.nested.server.handler;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.gwtplatform.samples.nested.server.dao.PersonDao;
import com.gwtplatform.samples.nested.shared.action.FindAllPersonsAction;
import com.gwtplatform.samples.nested.shared.action.FindAllPersonsResult;
import com.gwtplatform.samples.nested.shared.domain.Person;

public class FindAllPersonsHandler implements
		ActionHandler<FindAllPersonsAction, FindAllPersonsResult> {
	@Inject
	FindAllPersonsHandler() {
	}

	@Override
	public FindAllPersonsResult execute(final FindAllPersonsAction action,
			final ExecutionContext context) throws ActionException {
		PersonDao personDao = new PersonDao();
		List<Person> persons = personDao.findAllPersons();
		
		FindAllPersonsResult result = new FindAllPersonsResult();
		result.setPersons(persons);
		
		return result;
	}

	@Override
	  public Class<FindAllPersonsAction> getActionType() {
	    return FindAllPersonsAction.class;
	  }
	
	@Override
	public void undo(FindAllPersonsAction action, FindAllPersonsResult result,
			ExecutionContext context) throws ActionException {
	}
}
