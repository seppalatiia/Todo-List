package fi.my_work.todo_list.theming;

import javax.servlet.http.Cookie;

import fi.my_work.todo_list.cookies.CookieManagment;
import fi.my_work.todo_list.texts.T;

public class Theming {
//	This class looks up from cookie which theme should be used
	
	public static String getTheme(){
//		The default theme is Plain Grey
		String theme = T.system("THEME_PLAIN_GREY");
		
//		This line gets the theme cookie, if there is one
		Cookie themeCookie = CookieManagment.getCookie(T.system("COOKIE_NAME_THEME"));
		
//		If theme cookie isn't found, nothing is done
		if (themeCookie == null) {}
		
//		If theme cookie is found theme is set to that
		else{
			theme = themeCookie.getValue();
		}
		
		return theme;
	}
}