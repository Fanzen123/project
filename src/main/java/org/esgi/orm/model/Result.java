package org.esgi.orm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("result")
public class Result {
	@ORM_PK
	public int id_dicipline, id_student;
	@ORM_PK
	public boolean bool_exam;
	public Double result;
	
	public Result() {}
	
	public Result(int id_dicipline, int id_student, boolean bool_exam,
			Double result) {
		super();
		this.id_dicipline = id_dicipline;
		this.id_student = id_student;
		this.bool_exam = bool_exam;
		this.result = result;
	}
	
	public static Result getCC(int id_dicipline, int id_user) {
		
		ORM orm = new ORM();
		
		// Specify fields
		String[] fields = new String[] {"id_dicipline", "id_student", "bool_exam", "result"};
		// Specify conditions
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id_dicipline", id_dicipline);
		conditions.put("id_student", id_user);
		conditions.put("bool_exam", "0");
		
		List<Object> list = orm.makeSelect(Result.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		
		if(list == null || list.size() == 0)
			return null;
		
		return (Result)list.get(0);
		
	}
	
	public static Result getExam(int id_dicipline, int id_user) {
		ORM orm = new ORM();
		
		// Specify fields
		String[] fields = new String[] {"id_dicipline", "id_student", "bool_exam", "result"};
		// Specify conditions
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id_dicipline", id_dicipline);
		conditions.put("id_student", id_user);
		conditions.put("bool_exam", "1");
		
		List<Object> list = orm.makeSelect(Result.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		
		if(list == null || list.size() == 0)
			return null;
		
		return (Result)list.get(0);
	}

	@Override
	public String toString() {
		return "Result [id_dicipline=" + id_dicipline + ", id_student="
				+ id_student + ", bool_exam=" + bool_exam + ", result="
				+ result + "]";
	}
	
	
}
