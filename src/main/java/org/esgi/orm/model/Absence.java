package org.esgi.orm.model;


import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("absence")
public class Absence {
	
	
	@ORM_PK
	@ORM_AUTO_INCREMENT
	public int id_absence;
	
	public int id_student, id_dicipline;
	public String reason_absence, date_absence;
	
	@Override
	public String toString() {
		return "Absence [date absence=" + date_absence + "]";
	}
}
