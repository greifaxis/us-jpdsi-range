package com.jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Message;

@Stateless
public class MessageDAO {
	private final static String UNIT_NAME = "shootJPA";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public void create(Message message)
	{
		em.persist(message);
	}
	
	public int getRowsCount(String s)
	{
		if (s==null)
		{
			s="";
		}
		
		Query query = null;
		query = em.createQuery("SELECT COUNT (*) FROM Message m where m.isRead=0 and m.email like :s");
		query.setParameter("s", "%"+s+"%");
		return ((Long) query.getSingleResult()).intValue();
	}
	
	public List<Message> getFullList()
	{
		List<Message> list = null;
		
		Query query = em.createQuery("select m FROM Message m order by created_at desc").setFirstResult(0).setMaxResults(5);
		
		try {
		list = query.getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public String getMessageText(Integer id)
	{
		String message = "";
		Query query = em.createQuery("select text from Message where id =:id");
		query.setParameter("id", id);
		try
		{
			message = query.getSingleResult().toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return message;
	}
	
	public List<Message> getSearchedList(String s, int offset)
	{
		List<Message> list = null;
		if(s==null)
		{
			s="";
		}
		
		Query query = em.createQuery("select m FROM Message m where m.isRead = 0 and m.email like :s order by created_at desc");
		//Query query = em.createQuery("select m FROM Message m where m.isRead = 0 and (m.email like :s or m.date like:s)");
		query.setParameter("s", "%"+s+"%");
		query.setFirstResult(offset*5-5);
		query.setMaxResults(5);
		try {
			list = query.getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(list.isEmpty())
		{
			list.clear();
		}
		return list;
	}
}
