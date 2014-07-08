package org.esgi.module.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.model.Dicipline;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Support extends AbstractAction {
		
		public HashMap<String, ArrayList<org.esgi.orm.model.Support>> list;
		
		@Override
		public void execute(IContext context) throws Exception {
			list = new HashMap<String, ArrayList<org.esgi.orm.model.Support>>();
			ORM orm = new ORM();
			// We need to get every dicipline
			// Specify fields
			String[] fields = new String[] {"id_dicipline",  "id_class",  "id_teacher", "coef_dicipline", "libelle_dicipline"};
			// Specify conditions
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("1", "1");
			
			List<Object> l_dicipline = orm.makeSelect(Dicipline.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
			ArrayList<org.esgi.orm.model.Support> l_s = new ArrayList<org.esgi.orm.model.Support>();
			
			for(Object d : l_dicipline) {
				Dicipline d_ = (Dicipline)d;
				
				// Specify fields
				fields = new String[] {"id_support",  "title_support",  "date_support", "addr_support", "id_dicipline"};
				// Specify conditions
				conditions = new HashMap<String, Object>();
				conditions.put("id_dicipline", d_.id_dicipline);
				
				List<Object> l_support = orm.makeSelect(org.esgi.orm.model.Support.class, fields, conditions, null, null, null,  ORM.createConnectionObject());
				
				if(l_support != null) {
					for(Object s : l_support) {
						l_s.add((org.esgi.orm.model.Support) s);
					}
				}
				
				list.put(d_.libelle_dicipline, l_s);
			}
			
			context.getVelocityContext().put("iterator", list.entrySet().iterator());
			
		}
		
		@Override
		public String getRoute() {
			return "/support";
		}

}
