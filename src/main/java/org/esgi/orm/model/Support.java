package org.esgi.orm.model;

import org.esgi.orm.annotations.ORM_AUTO_INCREMENT;
import org.esgi.orm.annotations.ORM_PK;
import org.esgi.orm.annotations.ORM_TABLE;

@ORM_TABLE("support")
public class Support {
	@ORM_PK
	@ORM_AUTO_INCREMENT
	

	public int id_support;
	public String title_support, date_support, addr_support;
	public int id_dicipline;

	public Support() {}
	
	public Support(String title_support, String date_support,
			String addr_support, int id_dicipline) {
		super();
		this.title_support = title_support;
		this.date_support = date_support;
		this.addr_support = addr_support;
		this.id_dicipline = id_dicipline;
	}

	public int getId_support() {
		return id_support;
	}


	public String getTitle_support() {
		return title_support;
	}


	public String getDate_support() {
		return date_support;
	}


	public String getAddr_support() {
		return addr_support;
	}


	public int getId_dicipline() {
		return id_dicipline;
	}


	@Override
	public String toString() {
		return "\"" + id_support + "\"";
	}
}
