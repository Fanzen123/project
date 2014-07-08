package org.esgi.module.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.model.Dicipline;
import org.esgi.orm.model.Result;
import org.esgi.orm.model.Teacher;
import org.esgi.orm.model.User;
import org.esgi.web.Context;
import org.esgi.web.FrontController;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class NewResult extends AbstractAction {
	
	@Override
	public void execute(IContext context) throws Exception {
		
		User u = (User)((Context)context).session.getAttribute(FrontController.NAMESESSIONUSER);
		/*
		if((context.getParameter(NAME_ATTRIBUT_IDDICIPLINE) == null
				|| context.getParameter(NAME_ATTRIBUT_IDSTUDENT) == null
				|| context.getParameter(NAME_ATTRIBUT_EXAM) == null)
				
				&&
				
				(context.getParameter(NAME_ATTRIBUT_IDDICIPLINE) == null
				|| context.getParameter(NAME_ATTRIBUT_EXAM) == null
				||  context.getParameter(NAME_ATTRIBUT_RESULT) == null)) {
				
			return;
		}
		*/
		ORM orm = new ORM();
		
		if(context.getRequest().getParameter(Resultat.NAME_ATTRIBUT_ACTION).equals(Resultat.NAME_ACTION_DB)) {
			String idDicipline = (String)context.getRequest().getParameter(Resultat.NAME_ATTRIBUT_IDDICIPLINE);
			boolean isExam = ((String)context.getRequest().getParameter(Resultat.NAME_ATTRIBUT_EXAM)).equals("true") ? true : false;
			Double number = Double.valueOf((String) context.getRequest().getParameter(Resultat.NAME_ATTRIBUT_RESULT));
			
			Result res = new Result(Integer.parseInt(idDicipline), u.id_user, isExam, number);
			
			orm.makeInsert(res, ORM.createConnectionObject());
			
			return;
		}
		else if(context.getRequest().getParameter(Resultat.NAME_ATTRIBUT_ACTION).equals(Resultat.NAME_ACTION_FORM)) {
			
			boolean isExam = false;
			
			if(context.getRequest().getParameter(Resultat.NAME_ATTRIBUT_EXAM).equals("true")) {
				isExam = true;
			}
			
			// Specify fields
			String[] fields = new String[] {"id_dicipline",  "id_class",  "id_teacher", "coef_dicipline", "libelle_dicipline"};
			// Specify conditions
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("id_dicipline", context.getRequest().getParameter(Resultat.NAME_ATTRIBUT_IDDICIPLINE));
			
			List<Object> list = orm.makeSelect(Dicipline.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
			
			if(list == null || list.get(0) == null)
				return;
			
			Dicipline d = (Dicipline)list.get(0);
			
			// Specify fields
			fields = new String[] {"id_teacher",  "name_teacher",  "lastName_teacher"};
			// Specify conditions
			conditions = new HashMap<String, Object>();
			conditions.put("id_teacher", d.id_teacher);
			
			list = orm.makeSelect(Teacher.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
			
			if(list == null || list.get(0) == null)
				return;
			
			Print_Result p = new Print_Result((Teacher) list.get(0), d, isExam);
			
			List<String> numbers = new ArrayList<String>();
			for(int i = 1; i <= 20; i++) {
				numbers.add(i + "");
			}
			
			context.getVelocityContext().put("print", p);
			context.getVelocityContext().put("numbers", numbers);
			
			context.getVelocityContext().put("NAME_ATTRIBUT_ACTION", Resultat.NAME_ATTRIBUT_ACTION);
			context.getVelocityContext().put("NAME_ATTRIBUT_RESULT", Resultat.NAME_ATTRIBUT_RESULT);
			context.getVelocityContext().put("NAME_ATTRIBUT_IDDICIPLINE", Resultat.NAME_ATTRIBUT_IDDICIPLINE);
			context.getVelocityContext().put("NAME_ATTRIBUT_EXAM", Resultat.NAME_ATTRIBUT_EXAM);
		}
		
	}
	
	@Override
	public String getRoute() { 
		return "/new_result";
	}
	
	@Override
	public String getLayout() {
		return "just_contents";
	}
	
	public class Print_Result {
		
		public Teacher teacher;
		public Dicipline dicipline;
		public int number;
		public boolean isExam;
		
		public Print_Result(Teacher teacher, Dicipline dicipline, boolean exam) {
			super();
			this.teacher = teacher;
			this.dicipline = dicipline;
			isExam = exam;
		}

		public String getTeacherP() {
			return teacher.getName_teacher();
		}

		public String getDiciplineP() {
			return dicipline.getLibelle_dicipline();
		}

		public String getDicipline() {
			return dicipline.id_dicipline + "";
		}
		
		public Double getCoefficientP() {
			return dicipline.getCoef_dicipline();
		}

		public boolean isExam() {
			return isExam;
		}
		
		
	}
}
