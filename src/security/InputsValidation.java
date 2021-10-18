package security;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;


public class InputsValidation {

	
	public static boolean isValid(String type ,String value) {

		
		switch(type){
		case"email":return value!=null&&value.matches(".+@.+\\..+");
		case"alphanumeric":return value!=null&&value.matches("[_a-zA-Z0-9]+");
		case"alphabet":return value!=null&&value.matches("([_a-zA-Z]+\\s?){3,}");
		case"alphabetNoSpace":return value!=null&&value.matches("[a-zA-Z]+");
		case "numeric":return value!=null&&value.matches("[0-9]+");
		case "numericPos":return value!=null&&value.matches("[1-9]([0-9]+)?");
		case"date":return value!=null&&value.matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}");
		case"alphNumSpace":return  value!=null&&value.matches("([_a-zA-Z0-9]+\\s?)+");
		case"gender":return  value!=null&&(value.equals("male")||value.equals("female"));
		}
		return false;
	}
	
	 static boolean isValidExtension(String extension,String value) {
		return value!=null&&value.toLowerCase().endsWith(extension.toLowerCase());
	}
	public static String getExtension(String fileName) {
		if(isValidExtension(".png", fileName))
			return ".png";
		else if(isValidExtension(".jpeg", fileName))
			return ".jpeg";
		else if(isValidExtension(".jpg", fileName))
			return ".jpg";
		return null;
		
		
	}
	
	public static boolean isLogIn(HttpSession ses){
		try {
		boolean isLogIn= Boolean.parseBoolean((String)ses.getAttribute("isLogIn"));
		if(!isLogIn)
			return false;
		return true;
		}catch(Exception e) {
			return false;
		}
	}
	public static HashMap <String, Integer> getSessionInfo(HttpSession ses){
		try {

		int id=Integer.parseInt((String)ses.getAttribute("userId"));
		int role=Integer.parseInt((String)ses.getAttribute("role"));
		
		HashMap<String, Integer> info =new HashMap<String, Integer>();
		info.put("userId", id);
		info.put("role", role);
		return info;
		}catch(Exception e) {
			return null;
		}
		
		
	}
	
	public static int daysBetween(Date date1,Date date2) {
		LocalDate d1=new java.sql.Date(date1.getTime()).toLocalDate();
		LocalDate d2=new java.sql.Date(date2.getTime()).toLocalDate();
		return (int)ChronoUnit.DAYS.between(d1, d2);
	}
	
}
