package org.esgi.orm.model;


import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("delay")
public class Delay {
	
	
	@ORM_PK
	@ORM_AUTO_INCREMENT
	

	public int id_delay;
	public int id_student, id_dicipline;
	public String reason_delay, date_delay;
	
	@Override
	public String toString() {
		return "delay [date delay=" + date_delay + "]";
	}
}
