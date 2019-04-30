package com.jsf.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.jsf.dao.MessageDAO;
import com.jsf.dao.ReservationDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Message;
import com.jsf.entities.Reservation;
import com.jsf.entities.User;

@ViewScoped
@ManagedBean


public class AdminBB implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_RESERVATIONS = "reservations?faces-redirect=true";
	
	private List<Message> messageList = new ArrayList<>();
	private List<User> userList = new ArrayList<>();
	private List<Reservation> reservList = new ArrayList<>();
	private List<Reservation> reservArchivedList = new ArrayList<>();
	private String search="";
	
	private String message = "";
	private String user = "";
	private String reservation = "";

	private Integer pagesMessage=0;
	private Integer pagesUser=0;
	private Integer pagesReservations=0;
	
	private List<Integer> pagingUser = new ArrayList<>();
	private List<Integer> pagingMessage = new ArrayList<>();
	private List<Integer> pagingReservation = new ArrayList<>();
	
	@EJB
	MessageDAO messageDAO;
	@EJB
	UserDAO userDAO;
	@EJB
	ReservationDAO reservationDAO;
	
	public String confirm(Reservation reservation)
	{
		reservation.setIsConfirmed(true);
		try
		{
			reservationDAO.merge(reservation);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error deleting", null));
			return null;
		}
		return PAGE_RESERVATIONS;
		
	}
	
	public String removeReservation(Reservation reservation)
	{
		try
		{
			reservationDAO.delete(reservation);	
		}catch(Exception e)
		{
			e.printStackTrace();
			
			return PAGE_STAY_AT_THE_SAME;
		}
		
		return PAGE_RESERVATIONS;
	}
	
	//USERS
	public List<Integer> pagesUser()
	{
		pagingUser.clear();
		double temp = userDAO.getRowsCount(search)/10.0;
		pagesUser = (int) Math.ceil(temp);
		for(int i=0; i<pagesUser; i++)
		{
			pagingUser.add(i+1);
		}
		return null;
	}
	public List<User> searchedListUser(String search, int offset)
	{
		userList = userDAO.getSearchedList(search, offset);
		pagesUser();
		return null;
	}
	
	public List<User> getUserList() {
		if(userList.isEmpty())
		{
			userList = userDAO.getFullList();
		}
		pagesUser();
		return userList;
		
	}

	//MESSAGES
	public List<Integer> pagesMessage()
	{
		pagingMessage.clear();
		double temp = messageDAO.getRowsCount(search)/5.0;
		pagesMessage = (int) Math.ceil(temp);
		for(int i=0; i<pagesMessage; i++)
		{
			pagingMessage.add(i+1);
		}
		return null;
	}
	public List<Message> searchedList(String search, int offset)
	{
		messageList = messageDAO.getSearchedList(search, offset);
		pagesMessage();
		return null;
	}

	
	public List<Message> getMessageList() {
		if(messageList.isEmpty())
		{
			messageList = messageDAO.getFullList();
		}
		pagesMessage();
		return messageList;
		
	}
	public void showMessage(Integer id)
	{
		message = messageDAO.getMessageText(id);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  "Your message: " + message) );
	}
	
	//RESERVATIONS
	public List<Reservation> getReservList() {
		if(reservList.isEmpty())
		{
			reservList = reservationDAO.getFullListFalse();
		}
		return reservList;
	}
	
	public List<Reservation> getReservArchivedList() {
		if(reservArchivedList.isEmpty())
		{
			reservArchivedList = reservationDAO.getFullListTrue();
		}
		return reservArchivedList;
	}
	
	//GETTERS & SETTERS
	public void setReservList(List<Reservation> reservList) {
		this.reservList = reservList;
	}

	
	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getPagesMessage() {
		return pagesMessage;
	}
	public void setPagesMessage(Integer pages) {
		this.pagesMessage = pages;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<Integer> getPagingMessage() {
		return pagingMessage;
	}
	public void setPagingMessage(List<Integer> pagingMessage) {
		this.pagingMessage = pagingMessage;
	}
	public List<Integer> getPagingUser() {
		return pagingUser;
	}
	public void setPagingUser(List<Integer> pagingUser) {
		this.pagingUser = pagingUser;
	}
	public Integer getPagesUser() {
		return pagesUser;
	}
	public void setPagesUser(Integer pagesUser) {
		this.pagesUser = pagesUser;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getReservation() {
		return reservation;
	}
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}
	public Integer getPagesReservations() {
		return pagesReservations;
	}
	public void setPagesReservations(Integer pagesReservations) {
		this.pagesReservations = pagesReservations;
	}
	public List<Integer> getPagingReservation() {
		return pagingReservation;
	}
	public void setPagingReservation(List<Integer> pagingReservation) {
		this.pagingReservation = pagingReservation;
	}

	public void setReservArchivedList(List<Reservation> reservArchivedList) {
		this.reservArchivedList = reservArchivedList;
	}
}
