package org.esgi.orm.model;

import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("admin")
public class Admin {
	@ORM_PK
	@ORM_AUTO_INCREMENT
	

	public int id_admin;
	public String name_admin,lastName_admin;

	@Override
	public String toString() {
		return "\"" + name_admin + "\"";
	}
}
