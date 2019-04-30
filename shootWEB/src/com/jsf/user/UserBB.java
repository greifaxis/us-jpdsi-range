package com.jsf.user;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jsf.dao.GunDAO;
import com.jsf.dao.MessageDAO;
import com.jsf.dao.ReservationDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Gun;
import com.jsf.entities.Message;
import com.jsf.entities.Reservation;
import com.jsf.entities.User;


@ViewScoped
@ManagedBean
public class UserBB implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String PAGE_USER_RESERVE = "reserve?faces-redirect=true";
	private static final String PAGE_USER_SUCCESS = "weaponry?faces-redirect=true";
	private static final String PAGE_USER_HOME = "index?faces-redirect=true";
	private Date date = new Date();
	private User user = new User();
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	private Reservation reservation = new Reservation();
	private List<Integer> standList = new ArrayList<>();
	private List<Reservation> reservationList = new ArrayList<>();

	
	@EJB
	GunDAO gunDAO;
	@EJB
	UserDAO userDAO;
	@EJB
	ReservationDAO reservationDAO;
	public Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}
	public String reserve(Integer id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("gunID", id);
		return PAGE_USER_RESERVE;
	}
	
	public List<Reservation> onLoadReservations() throws IOException
	{
		FacesContext context = FacesContext.getCurrentInstance();

		// load person passed through session
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		
		user = (User) session.getAttribute("user");
		if(reservationList.isEmpty())
		{
			reservationList = reservationDAO.getUserReservations(user.getId());
		}
		
		return reservationList;
	}
	
	public String updateUser()
	{
		try {
			userDAO.merge(user);
		}catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie uda³o siê edytowaæ profilu", null));
			return null;
		}
		return PAGE_USER_HOME;
	}
	
	public void onLoad() throws IOException
	{
		FacesContext context = FacesContext.getCurrentInstance();
		
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		reservation.setGun(gunDAO.getGun((Integer) session.getAttribute("gunID")));
	}

	public List<Integer> getStandList() {
		for (int i=1;  i<=9; i++)
		{
			standList.add(i);
		}
		return standList;
	}
	public void setStandList(List<Integer> standList) {
		this.standList = standList;
	}
	public void onLoadUser() throws IOException
	{
		FacesContext context = FacesContext.getCurrentInstance();

		// load person passed through session
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		
		user = (User) session.getAttribute("user");
	}
	public String reserveTerm(Integer id)
	{
		reservation.setCreatedAt(ZonedDateTime                    // Represent a moment as perceived in the wall-clock time used by the people of a particular region ( a time zone).
				.now(                            // Capture the current moment.
					    ZoneId.of( "Europe/Warsaw" )  // Specify the time zone using proper Continent/Region name. Never use 3-4 character pseudo-zones such as PDT, EST, IST. 
					)                                // Returns a `ZonedDateTime` object. 
					);
			
		reservation.setDateEnd(addHoursToJavaUtilDate(reservation.getDateStart(), 1));
		
		//TODO boolean
		reservation.setIsConfirmed(false);
		reservation.setUser(userDAO.find(id));
		reservation.setUpdatedAt(ZonedDateTime                    // Represent a moment as perceived in the wall-clock time used by the people of a particular region ( a time zone).
				.now(                            // Capture the current moment.
					    ZoneId.of( "Europe/Warsaw" )  // Specify the time zone using proper Continent/Region name. Never use 3-4 character pseudo-zones such as PDT, EST, IST. 
					)                                // Returns a `ZonedDateTime` object. 
					);
		try {
			reservationDAO.create(reservation);
		}catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie uda³o siê wys³aæ wiadomoœci", null));
			return null;
		}
		return PAGE_USER_SUCCESS;
	}
	
	
	public Date getDate() {
		
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	public List<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
}

