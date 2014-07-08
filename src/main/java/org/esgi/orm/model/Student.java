package org.esgi.orm.model;


import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("student")
public class Student {
	
	
	@ORM_PK
	@ORM_AUTO_INCREMENT
	

	public int id_student;
	public int id_class;
	public String lastName_student,name_student;

	@Override
	public String toString() {
		return "\"" + id_student + "\"";
	}
}
