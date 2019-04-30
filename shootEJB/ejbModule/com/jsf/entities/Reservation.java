package com.jsf.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.time.ZonedDateTime;


/**
 * The persistent class for the reservation database table.
 * 
 */
@Entity
@NamedQuery(name="Reservation.findAll", query="SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="created_at")
	private ZonedDateTime createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_end")
	private Date dateEnd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_start")
	private Date dateStart;

	@Column(name="is_confirmed")
	private boolean isConfirmed;

	@Column(name="stand_id")
	private int standId;

	@Column(name="updated_at")
	private ZonedDateTime updatedAt;

	//bi-directional many-to-one association to Gun
	@ManyToOne
	private Gun gun;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Reservation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ZonedDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(ZonedDateTime zonedDateTime) {
		this.createdAt = zonedDateTime;
	}

	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public boolean getIsConfirmed() {
		return this.isConfirmed;
	}

	public void setIsConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public int getStandId() {
		return this.standId;
	}

	public void setStandId(int standId) {
		this.standId = standId;
	}

	public ZonedDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime zonedDateTime) {
		this.updatedAt = zonedDateTime;
	}

	public Gun getGun() {
		return this.gun;
	}

	public void setGun(Gun gun) {
		this.gun = gun;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}