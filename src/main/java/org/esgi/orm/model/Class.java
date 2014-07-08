package org.esgi.orm.model;


import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("class")
public class Class {
	
	
	@ORM_PK
	@ORM_AUTO_INCREMENT
	

	public int id_class;
	public String promo_class;
	
	@Override
	public String toString() {
		return "Classe [libelle=" + promo_class +"]";
	}
}
