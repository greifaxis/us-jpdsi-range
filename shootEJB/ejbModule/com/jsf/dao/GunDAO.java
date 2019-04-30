package com.jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Gun;

@Stateless
public class GunDAO {
	private final static String UNIT_NAME = "shootJPA";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public void create(Gun gun)
	{
		em.persist(gun);
	}
	public Gun getGun(Integer id)
	{
		Gun gun = new Gun();
		Query query = em.createQuery("select g FROM Gun g where id =:id");
		query.setParameter("id", id);
		try {
		gun = (Gun) query.getSingleResult();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return gun;
	}
	public List<Gun> getFullList()
	{
		List<Gun> list = null;
		
		Query query = em.createQuery("select g FROM Gun g order by name");
				
		
		list = query.getResultList();
		
		return list;
	}
	
}
