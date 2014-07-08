package org.esgi.module.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.model.Dicipline;
import org.esgi.orm.model.Result;
import org.esgi.orm.model.User;
import org.esgi.web.Context;
import org.esgi.web.FrontController;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;


public class Resultat extends AbstractAction {

	public static String NAME_ATTRIBUT_IDDICIPLINE = "idDicipline", NAME_ATTRIBUT_IDSTUDENT = "idStudent", NAME_ATTRIBUT_EXAM = "isExam",
			NAME_ATTRIBUT_ACTION = "action", NAME_ACTION_DB = "createDataInDBB", NAME_ACTION_FORM = "createForm",
			NAME_ATTRIBUT_RESULT = "number", NAME_ACTION_DELETE = "delete",  NAME_ACTION_UPDATE = "update";
	
	ArrayList<Res_print> l_ = null;
	
	@Override
	public void execute(IContext context) throws Exception {
		User u = (User)((Context)context).session.getAttribute(FrontController.NAMESESSIONUSER);
		
		if(u == null || u.getId_user() == null || u.getId_user() <= 0)
			return;
		
		ORM orm = new ORM();
		
		if(context.getRequest().getParameter(NAME_ATTRIBUT_ACTION) != null 
			&& context.getRequest().getParameter(NAME_ATTRIBUT_ACTION).equals(NAME_ACTION_UPDATE)) {
				int idDicipline = Integer.parseInt(context.getRequest().getParameter(NAME_ATTRIBUT_IDDICIPLINE));
				boolean isExam = ((String) context.getRequest().getParameter(NAME_ATTRIBUT_EXAM)).equals("true") ? true : false;
				Double number = Double.valueOf((String) context.getRequest().getParameter(NAME_ATTRIBUT_RESULT));
				
				Result r = new Result(idDicipline, u.getId_user(), isExam, number);
				
				System.out.println(r);
				
				orm.makeUpdate(r, ORM.createConnectionObject());
				
				return;
		}
		else if(context.getRequest().getParameter(NAME_ATTRIBUT_ACTION) != null 
				&& context.getRequest().getParameter(NAME_ATTRIBUT_ACTION).equals(NAME_ACTION_DELETE)) {
				int idDicipline = Integer.parseInt(context.getRequest().getParameter(NAME_ATTRIBUT_IDDICIPLINE));
				boolean isExam = ((String) context.getRequest().getParameter(NAME_ATTRIBUT_EXAM)).equals("true") ? true : false;
				
				Result r = new Result(idDicipline, u.getId_user(), isExam, null);
				
				orm._remove(Result.class, r);
				
				return;
		}
		else {
			l_ = new ArrayList<Res_print>();
			
			//Get Result from BD for every dicipline
			// Specify fields
			String[] fields = new String[] {"id_dicipline",  "id_class",  "id_teacher", "coef_dicipline", "libelle_dicipline"};
			// Specify conditions
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("1", "1");
			
			List<Object> list = orm.makeSelect(Dicipline.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
			for(Object d : list) {
				Dicipline d_ = (Dicipline)d;
				
				Result res_cc = Result.getCC(d_.id_dicipline, u.id_user);
				Result res_exam = Result.getExam(d_.id_dicipline, u.id_user);
				
				double number_cc = -1, number_exam = -1;
				
				if(res_cc != null)
					number_cc = res_cc.result;
				if(res_exam != null)
					number_exam = res_exam.result;
					
				
				Res_print rp = new Res_print(d_.libelle_dicipline, (d_.getTeacher() != null) ? d_.getTeacher().getName_teacher() : "", number_cc , number_exam, d_.coef_dicipline, d_.id_dicipline);
				
				l_.add(rp);
				
			}
			 
			// LOAD DATA ABOUT RESULT
			List<String> numbers = new ArrayList<String>();
			for(int i = 0; i <= 20; i++) {
				numbers.add(i + "");
			}
			
			context.getVelocityContext().put("numbers", numbers);
			context.getVelocityContext().put("list_", l_);
			
			context.getVelocityContext().put("NAME_ACTION_FORM", NAME_ACTION_FORM);
			context.getVelocityContext().put("NAME_ACTION_DELETE", NAME_ACTION_DELETE);
			context.getVelocityContext().put("NAME_ACTION_UPDATE", NAME_ACTION_UPDATE);
			context.getVelocityContext().put("NAME_ATTRIBUT_RESULT", NAME_ATTRIBUT_RESULT);
			context.getVelocityContext().put("NAME_ATTRIBUT_ACTION", NAME_ATTRIBUT_ACTION);
			context.getVelocityContext().put("NAME_ATTRIBUT_EXAM", NAME_ATTRIBUT_EXAM);
			context.getVelocityContext().put("NAME_ATTRIBUT_IDDICIPLINE", NAME_ATTRIBUT_IDDICIPLINE);
		}
	}
	
	public class Res_print {
		public String dicipline, teacher;
		public Double cc, exam, coef;
		public int id;
		
		public Res_print(String dicipline, String teacher, double cc,
				Double exam, Double coef, int id_dicipline) {
			super();
			this.dicipline = dicipline;
			this.teacher = teacher;
			this.cc = cc;
			this.exam = exam;
			this.coef = coef;
			this.id = id_dicipline;
		}
		
		public int getId() {
			return id;
		}

		public String getDicipline() {
			return dicipline;
		}

		public String getTeacher() {
			return teacher;
		}

		public String getCc() {
			if(cc < 0)
				return null;
			else
				return String.valueOf(cc);
		}

		public String getExam() {
			if(exam < 0)
				return null;
			else
				return String.valueOf(exam);
		}

		public String getCoef() {
			return String.valueOf(coef);
		}
	}
	
	@Override
	public String getRoute() {
		return "/resultat";
	}

}
