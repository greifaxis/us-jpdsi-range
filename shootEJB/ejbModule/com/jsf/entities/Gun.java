package com.jsf.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the gun database table.
 * 
 */
@Entity
@NamedQuery(name="Gun.findAll", query="SELECT g FROM Gun g")
public class Gun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String caliber;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="image_url")
	private String imageUrl;

	private int magazine;

	private String name;

	@Lob
	private String text;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	private double weight;

	//bi-directional many-to-one association to Reservation
	@OneToMany(mappedBy="gun")
	private List<Reservation> reservations;

	public Gun() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaliber() {
		return this.caliber;
	}

	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getMagazine() {
		return this.magazine;
	}

	public void setMagazine(int magazine) {
		this.magazine = magazine;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation addReservation(Reservation reservation) {
		getReservations().add(reservation);
		reservation.setGun(this);

		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		getReservations().remove(reservation);
		reservation.setGun(null);

		return reservation;
	}

}