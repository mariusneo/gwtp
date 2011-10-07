package com.gwtplatform.samples.nested.server.handler;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.gwtplatform.samples.nested.server.dao.PersonDao;
import com.gwtplatform.samples.nested.shared.action.FindPersonsAction;
import com.gwtplatform.samples.nested.shared.action.FindPersonsResult;
import com.gwtplatform.samples.nested.shared.domain.Person;

public class FindPersonsHandler implements
		ActionHandler<FindPersonsAction, FindPersonsResult> {
	@Inject
	FindPersonsHandler() {
	}

	@Override
	public FindPersonsResult execute(final FindPersonsAction action,
			final ExecutionContext context) throws ActionException {
		PersonDao personDao = new PersonDao();
		List<Person> persons = personDao.findPersons(action.getLastName());

		FindPersonsResult result = new FindPersonsResult();
		result.setPersons(persons);

		return result;
	}

	@Override
	public Class<FindPersonsAction> getActionType() {
		return FindPersonsAction.class;
	}

	@Override
	public void undo(FindPersonsAction action, FindPersonsResult result,
			ExecutionContext context) throws ActionException {
	}
}
