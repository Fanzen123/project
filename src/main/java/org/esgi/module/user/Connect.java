package org.esgi.module.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.orm.ORM;
import org.esgi.orm.model.User;
import org.esgi.web.Context;
import org.esgi.web.FrontController;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Connect extends AbstractAction{
	String[] projections = new String[]{"id_user","login_user","pwd_user", "connexion_user"};
	Map<String, Object> where = new HashMap<String, Object>();
	
	@Override
	public String getRoute() {
		return "/connect";
	}
	@Override
	public String getLayout() {
		return null;
	}
	@Override
	public void execute(IContext context) throws Exception {
		// User want to be disconnect
		if(context.getRequest().getParameter("deconnect") != null) {
			((Context)context).session.setAttribute(FrontController.NAMESESSIONUSER, null);
			context.getVelocityContext().put("user", null);
			return;
		}
		
		if(((Context)context).session.getAttribute(FrontController.NAMESESSIONUSER) != null) {
			return;
		}
		
		where.put("login_user", context.getRequest().getParameter("login"));
		where.put("pwd_user", context.getRequest().getParameter("password"));
		
		try
		{
			ORM orm = new ORM();
			
			List<Object> list = orm.makeSelect(User.class, projections, where, null, null, null, ORM.createConnectionObject());
			
			if(list != null && list.size() > 0) {
				// LOADING DATA FROM DB
				User u = (User)list.get(0);
				// PUTTING DATA USER IN SESSION AND CONTEXT
				((Context)context).session.setAttribute(FrontController.NAMESESSIONUSER, u);
				context.getVelocityContext().put("user", u);
				context.getResponse().getOutputStream().write(new String("1").getBytes());
			}
		}
		catch(Exception ex)
		{
			context.getResponse().sendRedirect("IllegalArgument");
		}

	}
}
