package org.esgi.orm.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("user")
public class User {
	
	
	@ORM_PK
	@ORM_AUTO_INCREMENT
	public Integer id_user;
	public String login_user;
	public String pwd_user;
	public volatile Date connexion_user;
	
	public User() {}
	
	public User(Integer id) {
		super();
		this.id_user = id;
	}

	public User(Integer id, String login, String password, Date connectedAt) {
		super();
		this.id_user = id;
		this.login_user = login;
		this.pwd_user = password;
		this.connexion_user = connectedAt;
	}

	public boolean isEleve() {
		// Init ORM
		ORM orm = new ORM();
		
		// Specify fields
		String[] fields = new String[] {"id_student"};
		// Specify conditions
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put("id_student", id_user);
		// Get Eleve with the ID
		List<Object> list = orm.makeSelect(Student.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		
		if(list != null && list.size() > 0)
			return true;
		else
			return false;
	}
	
	public Integer getIdDicipline() {
		if(isEleve())
			return null;
		
		// Init ORM
		ORM orm = new ORM();
		
		// Specify fields
		String[] fields = new String[] {"id_dicipline"};
		// Specify conditions
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put("id_teacher", id_user);
		List<Object> list = orm.makeSelect(Dicipline.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		
		if(list != null && list.size() > 0)
			return ((Dicipline)list.get(0)).id_dicipline;
		else
			return null;
	}
	
	@Override
	public String toString() {
		return "User [login=" + login_user + ", password=" + pwd_user
				+ ", connectedAt=" + connexion_user + "]";
	}

	public Integer getId_user() {
		return id_user;
	}

	public String getLogin_user() {
		return login_user;
	}
	
	
}
