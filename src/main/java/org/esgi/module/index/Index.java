
package org.esgi.module.index;

import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class Index extends AbstractAction{
	
	@Override
	public void execute(IContext context) throws Exception {
		context.setPageTitle("MYGES");
	}
	
	@Override
	public String getRoute() { 
		return "/index";
	}
	
}
