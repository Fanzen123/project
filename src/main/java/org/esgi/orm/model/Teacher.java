package org.esgi.orm.model;


import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("teacher")
public class Teacher {
	
	@ORM_PK
	@ORM_AUTO_INCREMENT
	public int id_teacher;
	public String name_teacher, lastName_teacher;
	
	public Teacher() {}
	
	public Teacher(int id_teacher, String name_teacher, String lastName_teacher) {
		super();
		this.id_teacher = id_teacher;
		this.name_teacher = name_teacher;
		this.lastName_teacher = lastName_teacher;
	}


	public String getName_teacher() {
		return name_teacher;
	}



	public void setName_teacher(String name_teacher) {
		this.name_teacher = name_teacher;
	}



	@Override
	public String toString() {
		return "teacher [name=" + name_teacher + "]";
	}
}
