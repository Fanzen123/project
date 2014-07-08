package org.esgi.module.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.esgi.orm.ORM;
import org.esgi.orm.model.User;
import org.esgi.web.Context;
import org.esgi.web.FrontController;
import org.esgi.web.action.AbstractAction;
import org.esgi.web.action.IContext;

public class NewSupport extends AbstractAction{
	
	public static String FORM_PARAM_NAME = "formSupport", LIBELLE_PARAM_NAME = "libelle" , FILE_PARAM_NAME = "file";
	public static String ADDRESS = "/myges/res/files/";
	@Override
	public void execute(IContext context) throws Exception {
		
		User u = (User)((Context)context).session.getAttribute(FrontController.NAMESESSIONUSER);
		
		// if(u == null || u.id_user == null || u.getIdDicipline() == null)
		if(u == null || u.id_user == null )
			return;
		List<FileItem> items;
		boolean bool = false;
		try {
			items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(context.getRequest());
			bool = true;
		} catch (Exception e) {}
        
		String libelle = null;
		
		if(bool) {
			 try {
				items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(context.getRequest());
				if(items.size() == 0)
					System.out.println("items empty or size=0");
		        for (FileItem item : items) {
		        	System.out.println("Looping...");
		        	if (item.isFormField()) {
		        		if(item.getFieldName().equals(LIBELLE_PARAM_NAME)) {
		        			System.out.println(item.toString());
		        		}
		        	}
		        	else if (!item.isFormField()) {
		                // Traiter les champs de type fichier (input type="file").
		                String fieldname = item.getFieldName();
		                String filename = FilenameUtils.getName(item.getName());
		                InputStream filecontent = item.getInputStream();
		                
		                String v = getValue(filecontent);
		                boolean writeInFile = writeValue(v, fieldname);
		                
		                if(!writeInFile)
		                	return;
		            }
		        }
		    } catch (FileUploadException e) {
		    	e.printStackTrace();
		        throw new ServletException("Cannot parse multipart request.", e);
		    }
			 
			org.esgi.orm.model.Support s = new org.esgi.orm.model.Support(
					context.getRequest().getParameter(LIBELLE_PARAM_NAME), new Date().toString(), ADDRESS, u.getIdDicipline());
			
			ORM orm = new ORM();
			
			orm.makeInsert(s, ORM.createConnectionObject());
		}
		
		context.getVelocityContext().put("FORM_PARAM_NAME", FORM_PARAM_NAME);
		context.getVelocityContext().put("LIBELLE_PARAM_NAME", LIBELLE_PARAM_NAME);
		context.getVelocityContext().put("FILE_PARAM_NAME", FILE_PARAM_NAME);
		
	}
	
	@Override
	public String getRoute() { 
		return "/new_support";
	}
	
	@Override
	public String getLayout() {
		return "just_contents";
	}
	
	private static String getValue(InputStream i) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(i, "UTF-8"));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
	}
	
	private static boolean writeValue(String value, String name) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(name));
	    writer.append(value);
	    writer.flush();
	    writer.close();
	    return true;
	}
	
	
}
