package com.jsf.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.jsf.entities.User;


@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "shootJPA";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public void create(User user) {
		em.persist(user);
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}

	public User find(Object id) {
		return em.find(User.class, id);
	}
	public int getRowsCount(String s)
	{
		if(s==null)
		{
			s="";
		}
		Query query = null;
		
			query = em.createQuery("SELECT COUNT(*) FROM User u where u.role = 'user' and u.active = 1 and (u.firstName like :s or u.lastName like :s) order by id");
			query.setParameter("s", "%"+s+"%");
		return ((Long) query.getSingleResult()).intValue();
	}
	
	public List<User> getFullList() {
		List<User> list = null;

		Query query = em.createQuery("select u FROM User u where u.role = 'user' and u.active = 1 order by id").setFirstResult(0).setMaxResults(10);
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		return list;
	}
	
	public List<User> getSearchedList(String s, int offset) {
		List<User> list = null;
		if(s==null)
		{
			s="";
		}
	
		Query query = em.createQuery("select u FROM User u where u.role = 'user' and u.active = 1 and (u.firstName like :s or u.lastName like :s) order by id");
		query.setParameter("s", "%"+s+"%");
		query.setFirstResult(offset*10-10);
		query.setMaxResults(10);
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
	
	
	// simulate finding user in DB
	public User getUserFromDatabase(String login, String pass) {
		User u = null;

		String select = "select u ";
		String from = "from User u ";
		String where = "where u.nickname = :login and u.password = :pass";

		Query query = em.createQuery(select + from + where);

		query.setParameter("login", login);
		query.setParameter("pass", pass);

		try {
			u = (User) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}
	
	public User getEditorFromDatabase(Integer id) {
		User u = null;

		String select = "select u ";
		String from = "from User u ";
		String where = "where u.id = :id";
		
		Query query = em.createQuery(select + from + where);

		query.setParameter("id", id);

		try {
			u = (User) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}
	public List<User> retrieveUsers(int first, int pageSize) {
		TypedQuery<User> query = null;
		
		
			query = em.createQuery("select u from User u", User.class)
				.setFirstResult(first).setMaxResults(pageSize);
		
		return query.getResultList();
	}
//	public List<User> getList(Map<String, Object> searchParams) {
//		List<User> list = null;
//
//		// 1. Build query string with parameters
//		String select = "select pesel, password ";
//		String from = "from Users ";
//		String where = "";
//
//		// search for surname
//		String surname = (String) searchParams.get("surname");
//		if (surname != null) {
//			if (where.isEmpty()) {
//				where = "where ";
//			} else {
//				where += "and ";
//			}
//			where += "p.surname like :surname ";
//		}
//		
//		// ... other parameters ... 
//
//		// 2. Create query object
//		Query query = em.createQuery(select + from + where);
//
//		// 3. Set configured parameters
//		if (surname != null) {
//			query.setParameter("surname", surname+"%");
//		}
//
//		// ... other parameters ... 
//
//		// 4. Execute query and retrieve list of Person objects
//		try {
//			list = query.getResultList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}

	// simulate retrieving roles of a User from DB
public List<String> getUserRolesFromDatabase(User user) {
	ArrayList<String> roles = new ArrayList<String>();

	roles.add(user.getRole());

	return roles;
	}

public void deactivateUser(User user) {
	String select = "select u ";
	String from = "from User u ";
	String where = "where id = :id";

	Query query = em.createQuery(select + from + where);

	query.setParameter("id", user.getId());

	User edited = null;
	try {
		edited = (User) query.getSingleResult();
	} catch (Exception e) {
		e.printStackTrace();
	}

	edited.setActive(false);

	merge(edited);
}
}
