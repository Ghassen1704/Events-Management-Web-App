package org.munic.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
	
	public static java.sql.Date ConvertDate(String olddate) throws ParseException {
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(olddate);  
		 String date_String=new SimpleDateFormat("yyyy-MM-dd").format(date);
		 java.sql.Date date_Formatted=java.sql.Date.valueOf(date_String);
		return date_Formatted;
	}
	
	public static String ConvertDateToString(java.sql.Date olddate) throws ParseException {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		String date_Formatted = df.format(olddate);  
		return date_Formatted;
	}
	
public static String ConvertDateToStringJson(java.sql.Date olddate) throws ParseException {
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");  
		String date_Formatted = df.format(olddate);  
		return date_Formatted;
	}
	
public static String ConvertDateTimeToString(java.util.Date olddate) throws ParseException {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");  
		String date_Formatted = df.format(olddate);  
		return date_Formatted;
	}
	
	

}
