package com.jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Reservation;
import com.jsf.entities.User;

@Stateless
public class ReservationDAO {
	private final static String UNIT_NAME = "shootJPA";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	public void create(Reservation reservation) {
		em.persist(reservation);
	}
	
	public Reservation merge(Reservation reservation) {
		return em.merge(reservation);
	}

	public void delete(Reservation reservation) {
		em.remove(em.merge(reservation));
	}

	public Reservation find(Object id) {
		return em.find(Reservation.class, id);
	}
	
	public List<Reservation> getUserReservations(Integer id)
	{
		List<Reservation> list = null;
		
		Query query = em.createQuery("select r FROM Reservation r where user_id =:id order by dateStart");
		query.setParameter("id", id);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
		
	public int getRowsCountFalse(String s)
	{
		if(s==null)
		{
			s="";
		}
		Query query = null;
		
			query = em.createQuery("SELECT COUNT(*) FROM Reservation r where r.isConfirmed = 0");
			query.setParameter("s", "%"+s+"%");
		return ((Long) query.getSingleResult()).intValue();
	}
	
	public List<Reservation> getSearchedListFalse(String s, int offset) {
		List<Reservation> list = null;
		if(s==null)
		{
			s="";
		}
	
		Query query = em.createQuery("select r FROM Reservation r where r.isConfirmed = 0 order by date_start");
		query.setParameter("s", "%"+s+"%");
		query.setFirstResult(offset*20-20);
		query.setMaxResults(20);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty())
		{
			list.clear();
		}
		return list;
	}
	
	
	
	public List<Reservation> getFullListFalse()
	{
		List<Reservation> list = null;

		Query query = em.createQuery("select r FROM Reservation r where r.isConfirmed = 0 order by date_start").setFirstResult(0).setMaxResults(20);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return list;
	}
	
	public int getRowsCountTrue(String s)
	{
		if(s==null)
		{
			s="";
		}
		Query query = null;
		
			query = em.createQuery("SELECT COUNT(*) FROM Reservation r where r.isConfirmed = 1 order by created_at desc");
			query.setParameter("s", "%"+s+"%");
		return ((Long) query.getSingleResult()).intValue();
	}
	
	public List<Reservation> getSearchedListTrue(String s, int offset) {
		List<Reservation> list = null;
		if(s==null)
		{
			s="";
		}
	
		Query query = em.createQuery("select r FROM Reservation r where r.isConfirmed = 1 order by created_at desc");
		query.setParameter("s", "%"+s+"%");
		query.setFirstResult(offset*20-20);
		query.setMaxResults(20);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty())
		{
			list.clear();
		}
		return list;
	}
	
	
	
	public List<Reservation> getFullListTrue()
	{
		List<Reservation> list = null;

		Query query = em.createQuery("select r FROM Reservation r where r.isConfirmed = 1 order by date_start").setFirstResult(0).setMaxResults(20);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return list;
	}
	
}
