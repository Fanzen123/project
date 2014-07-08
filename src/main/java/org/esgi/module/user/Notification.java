package org.esgi.module.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.model.Absence;
import org.esgi.orm.model.Dicipline;
import org.esgi.orm.model.Delay;
import org.esgi.orm.model.User;
import org.esgi.web.Context;
import org.esgi.web.FrontController;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Notification extends AbstractAction {

	public static final int ABSENCE = 0;
	public static final int RETARD = 0;
	
	ArrayList<oNotification> l_absences;
	ArrayList<oNotification> l_retards;
	private int numberOfAbsence = 0;
	private int numberOfRetard = 0;
	
	@Override
	public void execute(IContext context) throws Exception {
		
		/*
		 * Eleve or Teacher is connected ?
		 */
		
		User u = (User)((Context)context).session.getAttribute(FrontController.NAMESESSIONUSER);
		
		if(u == null || u.id_user == null)
			return;
		
		// Update operation
		if(context.getRequest().getParameter("id_to_update") != null) {
			if(context.getRequest().getParameter("type") != null && context.getRequest().getParameter("type").equals("absence")) {
				
				ORM orm = new ORM();
				
				// Specify fields
				String[] fields = new String[] {"id_absence",  "reason_absence",  "date_absence", "id_student", "id_dicipline"};
				// Specify conditions
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("id_absence", context.getRequest().getParameter("id_to_update"));
				
				// Get every Absence from an "eleve"
				List<Object> list = orm.makeSelect(Absence.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
				
				Absence a = (Absence)list.get(0);
				
				if(context.getRequest().getParameter("field") != null && context.getRequest().getParameter("field").equals("motif")) {
					a.reason_absence = context.getRequest().getParameter("value");
					orm.makeUpdate(a , ORM.createConnectionObject());
				} else if(context.getRequest().getParameter("field") != null && context.getRequest().getParameter("field").equals("date")) {
					a.date_absence = context.getRequest().getParameter("value");
					orm.makeUpdate(a , ORM.createConnectionObject());
				} else if(context.getRequest().getParameter("field") != null && context.getRequest().getParameter("field").equals("matiere")) {
					a.id_dicipline = Integer.parseInt(context.getRequest().getParameter("value"));
					orm.makeUpdate(a , ORM.createConnectionObject());
				} 

				
			} else if(context.getRequest().getParameter("type") != null && context.getRequest().getParameter("type").equals("retard")) {
				
				ORM orm = new ORM();
				
				// Specify fields
				String[] fields = new String[] {"id_delay",  "reason_delay",  "date_delay", "id_student", "id_dicipline"};
				// Specify conditions
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("id_delay", context.getRequest().getParameter("id_to_update"));
				
				// Get every Absence from an "eleve"
				List<Object> list = orm.makeSelect(Delay.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
				
				Delay r = (Delay)list.get(0);
				
				if(context.getRequest().getParameter("field") != null && context.getRequest().getParameter("field").equals("motif")) {
					r.reason_delay = context.getRequest().getParameter("value");
					orm.makeUpdate(r , ORM.createConnectionObject());
				} else if(context.getRequest().getParameter("field") != null && context.getRequest().getParameter("field").equals("date")) {
					r.date_delay = context.getRequest().getParameter("value");
					orm.makeUpdate(r , ORM.createConnectionObject());
				} else if(context.getRequest().getParameter("field") != null && context.getRequest().getParameter("field").equals("matiere")) {
					r.id_dicipline = Integer.parseInt(context.getRequest().getParameter("value"));
					orm.makeUpdate(r , ORM.createConnectionObject());
				} 
			}
		}
		
		// Delete operation
		if(context.getRequest().getParameter("id_to_delete") != null && context.getRequest().getParameter("type") != null) {
			context.getVelocityContext().put("print_delete", 1);
			//TODO REPROGRAMMER _remove
			if(context.getRequest().getParameter("type").equals("absence")) 
				ORM.remove(Absence.class, Integer.parseInt(context.getRequest().getParameter("id_to_delete")));
			else
				ORM.remove(Delay.class, Integer.parseInt(context.getRequest().getParameter("id_to_delete")));
		} else
			context.getVelocityContext().put("print_delete", 0);
			
		l_absences = new ArrayList<oNotification>();
		l_retards = new ArrayList<oNotification>();
		
		// Init ORM
		ORM orm = new ORM();
		
		// Specify fields
		String[] fields = new String[] {"id_absence",  "reason_absence",  "date_absence", "id_student", "id_dicipline"};
		// Specify conditions
		Map<String, Object> conditions = new HashMap<String, Object>();
		if(!u.isEleve()) {
			conditions.put("id_student", context.getRequest().getAttribute("id_student"));
		} else {
			conditions.put("id_student", ((User)((Context)context).session.getAttribute(FrontController.NAMESESSIONUSER)).id_user);
		}
		// Get every Absence from an "eleve"
		List<Object> list = orm.makeSelect(Absence.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		
		int countNotJustifiedAbsence = 0; // Init the number of absence
		
		for(Object absence : list) {
			Absence a = (Absence)absence;
			
			// Get matiere from absence
			fields = new String[] {"id_dicipline",  "libelle_dicipline"};
			conditions = new HashMap<String, Object>(); conditions.put("id_dicipline", a.id_dicipline);
			List<Object> m = orm.makeSelect(Dicipline.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
			Dicipline m_found = (Dicipline)m.get(0);
			// Build each data on an object
			
			oNotification oN = new oNotification(a.id_absence, a.date_absence, m_found, "Absence",
					(a.reason_absence == null || a.reason_absence.trim().length() == 0) ? false : true, (a.reason_absence == null) ? "" : a.reason_absence);
			// Insert in array
			l_absences.add(oN);
			
			if(!oN.justifie)
				countNotJustifiedAbsence++;
			
			numberOfAbsence++;
		}
		
		// Specify fields
		fields = new String[] {"id_delay",  "reason_delay",  "date_delay", "id_student", "id_dicipline"};
		// Specify conditions
		conditions = new HashMap<String, Object>();
		if(!u.isEleve()) {
			conditions.put("id_student", context.getRequest().getAttribute("id_eleve"));
		} else {
			conditions.put("id_student", ((User)((Context)context).session.getAttribute(FrontController.NAMESESSIONUSER)).id_user);
		}
		// Get every retard from an "eleve"
		list = orm.makeSelect(Delay.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		
		int countNotJustifiedRetard = 0; // Init the number of retard
		
		for(Object retard : list) {
			Delay r = (Delay)retard;
			
			// Get matiere from retard
			fields = new String[] {"id_dicipline",  "libelle_dicipline"};
			conditions = new HashMap<String, Object>(); conditions.put("id_dicipline", r.id_dicipline);
			List<Object> m = orm.makeSelect(Dicipline.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
			Dicipline m_found = (Dicipline)m.get(0);
			// Build each data on an object
			oNotification oN = new oNotification(r.id_delay, r.date_delay, m_found, "Retard",
					(r.reason_delay == null || r.reason_delay.trim().length() == 0) ? false : true, (r.reason_delay == null) ? "" : r.reason_delay);
			
			// Insert in array
			l_retards.add(oN);
			
			if(!oN.justifie)
				countNotJustifiedRetard++;
			
			numberOfRetard++;
		}
		
		/*
		 * WE NEED TO FIND MATIERE WICH WILL BE HIDDEN IN A SELECT CHOICE
		 */
		fields = new String[] {"id_dicipline",  "libelle_dicipline"};
		conditions = new HashMap<String, Object>(); conditions.put("1", "1"); // EVERY MATIERE
		List<Object> l = orm.makeSelect(Dicipline.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
		ArrayList<Dicipline> lm = new ArrayList<Dicipline>();
		for(Object m : l) {
			lm.add((Dicipline)m);
		}
		
		// LOAD DATA ABOUT DATE
		List<String> j = new ArrayList<String>(),m = new ArrayList<String>(),a = new ArrayList<String>();
		List<String> hours = new ArrayList<String>(),minuts = new ArrayList<String>();
		for(int i = 1; i <= 31; i++) {
			if(i < 10)
				j.add("0" + i);
			else 
				j.add(i + "");
		} for(int i = 1; i <= 12; i++) {
			if(i < 10)
				m.add("0" + i);
			else 
				m.add(i + "");
		} for(int i = 2013; i <= 2014; i++) {
			a.add(i + "");
		} for(int i = 8; i <= 21; i++) {
			if(i < 10)
				hours.add("0" + i);
			else 
				hours.add(i + "");
		} for(int i = 0; i <= 60; i = i +5) {
			if(i < 10)
				minuts.add("0" + i);
			else 
				minuts.add(i + "");
		}
		
		context.getVelocityContext().put("jours", j);
		context.getVelocityContext().put("mois", m);
		context.getVelocityContext().put("annees", a);
		context.getVelocityContext().put("hours", hours);
		context.getVelocityContext().put("minuts", minuts);
		
		context.getVelocityContext().put("matieres", lm);
		context.getVelocityContext().put("numberOfAbsence", numberOfAbsence);
		context.getVelocityContext().put("numberOfRetard", numberOfRetard);
		context.getVelocityContext().put("countNotJustifiedAbsence", countNotJustifiedAbsence);
		context.getVelocityContext().put("countNotJustifiedRetard", countNotJustifiedRetard);
		context.getVelocityContext().put("absences", l_absences);
		context.getVelocityContext().put("retards", l_retards);
		context.getVelocityContext().put("u", u);
		
	}
	
	@Override
	public String getRoute() { 
		return "/notification";
	}
	
	public class oNotification {
		public int id;
		public String date, type, justification;
		public Dicipline dicipline;
		public boolean justifie;
		
		public oNotification(int id, String date, Dicipline dicipline, String type,
				boolean justifie, String justification) {
			this.id = id;
			this.date = date;
			this.dicipline = dicipline;
			this.type = type;
			this.justifie = justifie;
			this.justification = justification;
		}
		
		public int getId() {
			return id;
		}

		public String getDate() {
			return date;
		}
		public String getDicipline() {
			return dicipline.libelle_dicipline;
		}
		public String getType() {
			return type;
		}
		public String isJustifie() {
			return (justifie) ? "Oui" : "Non";
		}
		public String getJustification() {
			return justification;
		}
	}
	


}
