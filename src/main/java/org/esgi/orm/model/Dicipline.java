package org.esgi.orm.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("dicipline")
public class Dicipline {
	
	
	@ORM_PK
	@ORM_AUTO_INCREMENT
	
	public int id_dicipline;
	public int id_class, id_teacher;
	public Double coef_dicipline;
	public String libelle_dicipline;
	
	
	public int getId_dicipline() {
		return id_dicipline;
	}



	public String getLibelle_dicipline() {
		return libelle_dicipline;
	}



	public Double getCoef_dicipline() {
		return coef_dicipline;
	}



	@Override
	public String toString() {
		return "dicipline [libelle=" + libelle_dicipline + "]";
	}
	
	public Teacher getTeacher() {
		ORM orm = new ORM();
		
		// Specify fields
		String[] fields = new String[] {"id_teacher",  "name_teacher",  "lastName_teacher"};
		// Specify conditions
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id_teacher", id_teacher);
		
		List<Object> list = orm.makeSelect(Teacher.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		
		if( list != null && list.get(0) != null)
			return (Teacher)list.get(0);
		else
			return null;
	}
}
