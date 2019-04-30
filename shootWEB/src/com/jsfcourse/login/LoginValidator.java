package com.jsfcourse.login;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

	private ResourceBundle messages;

	public ResourceBundle getMessages() {
		FacesContext context = FacesContext.getCurrentInstance();
		messages = ResourceBundle.getBundle("resources.regbean");
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}
	
	@PostConstruct
	public void PostConstruct() {		
		
	}
	
	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	int maximumlength = 11;

	public LoginValidator() {
	}

	@Override
	public void validate(FacesContext fc, UIComponent uic, Object obj)
			throws ValidatorException {
		String model = (String) obj;
		char[] array = model.toCharArray();
		for(int i=0; i<model.length()-1; i++)
		{
			if(!Character.isDigit(array[i]))
			{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,getMessages().getString("loginErrorChar"), null);
				
				throw new ValidatorException(msg);
			}
		}
		
		if (model.length() != 11) {
			FacesMessage msg = new FacesMessage(getMessages().getString("loginErrorLength"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(msg);
		}

	}
	
}
