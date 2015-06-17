package fi.my_work.todo_list.cookies;

import javax.servlet.http.Cookie;

import com.vaadin.server.VaadinService;

public class CookieManagment {
	public static void newCookie(String cookieName, String cookieValue) {

//		Create a new cookie
		Cookie cookie = new Cookie(cookieName, cookieValue);

//		Set the cookie path
		cookie.setPath(VaadinService.getCurrentRequest().getContextPath());

//		Save cookie
		VaadinService.getCurrentResponse().addCookie(cookie);
	}
	
	public static void saveCookie(String cookieName, String cookieValue) {
		Cookie cookie = getCookie(cookieName);
		
//		If the doesn't exist, create it
		if(cookie == null){
			newCookie(cookieName, cookieValue);
		}
//		If the does exist, destroy and re-create it
		else{
			destroyCookie(cookieName);
			newCookie(cookieName, cookieValue);
		}
	}

	public static Cookie getCookie(String cookieName) {

//		Fetch all cookies from the request
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

//		Iterate to find cookie by its name
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				return cookie;
			}
		}

		return null;

	}
	private static void destroyCookie(String cookieName){
		Cookie cookie = getCookie(cookieName);
		
		if (cookie != null) {
	        cookie.setValue(null);
	        
//	        By setting the cookie maxAge to 0 it will deleted immediately
	        cookie.setMaxAge(0);
	        cookie.setPath("/");
	        VaadinService.getCurrentResponse().addCookie(cookie);
	    }
	}
}