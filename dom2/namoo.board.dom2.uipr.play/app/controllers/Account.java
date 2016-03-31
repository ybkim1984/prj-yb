package controllers;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;


public class Account {
	@Required
	@Email
	public String email;
	@Required
	public String password;
}
