package org.esgi.module.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.esgi.orm.ORM;
import org.esgi.orm.model.Absence;
import org.esgi.orm.model.Dicipline;
import org.esgi.orm.model.Delay;
import org.esgi.orm.model.User;
import org.esgi.web.Context;
import org.esgi.web.FrontController;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class New extends AbstractAction {

	@Override
	public void execute(IContext context) throws Exception {
		HttpSession session = ((Context)context).session;
		User u = (User)session.getAttribute(FrontController.NAMESESSIONUSER);
		
		if(u == null)
			return;
		
		// Init ORM
		ORM orm = new ORM();
			
		if(context.getRequest().getParameter("create") != null) {
			// We must create the object and to create in BDD
			if(Integer.parseInt(context.getRequest().getParameter("type")) == 0) {
				Absence a = new Absence();
				a.id_student = u.id_user;
				a.id_dicipline = Integer.parseInt(context.getRequest().getParameter("matiere"));
				a.date_absence = context.getRequest().getParameter("date");
				a.reason_absence = context.getRequest().getParameter("justification");
				orm.makeInsert(a, ORM.createConnectionObject());
			} else {
				Delay r = new Delay();
				r.id_student = u.id_user;
				r.id_dicipline = Integer.parseInt(context.getRequest().getParameter("matiere"));
				r.date_delay = context.getRequest().getParameter("date");
				r.reason_delay = context.getRequest().getParameter("justification");
				orm.makeInsert(r, ORM.createConnectionObject());
			}
			
			return;
		}
		/*
		 * WE NEED TO FIND MATIERE WICH WILL BE HIDDEN IN A SELECT CHOICE
		 */
		String[] fields = new String[] {"id_dicipline",  "libelle_dicipline"};
		Map<String, Object> conditions = new HashMap<String, Object>(); conditions.put("1", "1"); // EVERY MATIERE
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
		
		if(context.getRequest().getParameter("new") != null && Integer.parseInt(context.getRequest().getParameter("new")) == 0) { 
			// SELECT ABSENCE MUST TO BE CHECKED
			context.getVelocityContext().put("checked_absence", "selected");
			context.getVelocityContext().put("checked_retard", "");
		} else { 
			// SELECT RETARD MUST TO BE CHECKED
			context.getVelocityContext().put("checked_absence", "");
			context.getVelocityContext().put("checked_retard", "selected");
		}
		
		context.getVelocityContext().put("matieres", lm);
		
		context.getVelocityContext().put("jours", j);
		context.getVelocityContext().put("mois", m);
		context.getVelocityContext().put("annees", a);
		context.getVelocityContext().put("hours", hours);
		context.getVelocityContext().put("minuts", minuts);
		
		context.getVelocityContext().put("u", u);
	}
	
	@Override
	public String getRoute() { 
		return "/new";
	}
	
	@Override
	public String getLayout() {
		return "just_contents";
	}
}
