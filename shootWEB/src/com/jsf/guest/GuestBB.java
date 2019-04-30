package com.jsf.guest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.jsf.dao.GunDAO;
import com.jsf.dao.MessageDAO;
import com.jsf.entities.Gun;
import com.jsf.entities.Message;


@ViewScoped
@ManagedBean
public class GuestBB implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Gun> gunList = new ArrayList<>();
	//MESSAGE
	private Message message = new Message();
	
	@EJB
	GunDAO gunDAO;
	@EJB
	MessageDAO messageDAO;
	
	//TODO Flash message
	public String sendMessage()
	{
		
		message.setIsRead(false);
		message.setCreatedAt(ZonedDateTime                    // Represent a moment as perceived in the wall-clock time used by the people of a particular region ( a time zone).
				.now(                            // Capture the current moment.
					    ZoneId.of( "Europe/Warsaw" )  // Specify the time zone using proper Continent/Region name. Never use 3-4 character pseudo-zones such as PDT, EST, IST. 
					)                                // Returns a `ZonedDateTime` object. 
					);
		try {
			messageDAO.create(message);
		}
		catch(Exception e)
		{
			
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie uda³o siê wys³aæ wiadomoœci", null));
				return null;
		}
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("registerSuccessful", "Message sent successfully");
		return null;
	}

	
	public List<Gun> getGunList()
	{
		gunList = gunDAO.getFullList();
		return gunList;
	}
	
	
	// GETTERS & SETTERS
	public void setGunList(List<Gun> gunList) {
		this.gunList = gunList;
	}


	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}

}
