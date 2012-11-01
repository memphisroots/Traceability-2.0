package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.RandomPassword;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.validate.AddPersonnelValidator;

/**
 * Used for Add Personnel page (addPersonnel.jsp). This just adds an empty LT, creates a random password
 * for them.
 * 
 * 
 */
public class AddLTAction {
	private PersonnelDAO personnelDAO;
	private AuthDAO authDAO;
	/**
	 * Sets up the defaults for the class
	 * 
	 * @param factory factory for creating the defaults.
	 * @param loggedInMID person currently logged in 	
	 */	
	
	public AddLTAction(DAOFactory factory, long loggedInMID) {
		this.personnelDAO = factory.getPersonnelDAO();
		this.authDAO = factory.getAuthDAO();
	}
	
	/**
	 * Adds the new user.   Event is logged.
	 * 
	 * @param p bean containing the information for the new user
	 * @return MID of the new user.
	 * @throws FormValidationException
	 * @throws iTrustException
	 */
	public long add(PersonnelBean p) throws FormValidationException, iTrustException {
		new AddPersonnelValidator().validate(p);
		long newMID = personnelDAO.addEmptyPersonnel(Role.LT);
		p.setMID(newMID);
		personnelDAO.editPersonnel(p);
		String pwd = authDAO.addUser(newMID, Role.LT, RandomPassword.getRandomPassword());
		p.setPassword(pwd);
		return newMID;
	}

}
