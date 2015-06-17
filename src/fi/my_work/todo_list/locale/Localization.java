package fi.my_work.todo_list.locale;

import java.util.Locale;

import javax.servlet.http.Cookie;

import com.vaadin.ui.UI;

import fi.my_work.todo_list.cookies.CookieManagment;
import fi.my_work.todo_list.texts.T;

public class Localization {
//	This method gets the locale cookie and sets the locale of the UI to the one in the locale cookie
	public static void setLocale() {
		Cookie locale = CookieManagment.getCookie(T.system("COOKIE_NAME_LOCALE"));
		
		if(locale == null){
			UI.getCurrent().setLocale(UI.getCurrent().getLocale());
		}
		else{
			String values[] = locale.getValue().split("_");
			UI.getCurrent().setLocale(new Locale(values[0], values[1]));
		}
	}

	public static String getLocale() {
		String locale = UI.getCurrent().getLocale().toString();
		
/*
 * 		FIXME_LATER Fix this on later date
 * 		For some reason my Chrome sets locale as en_US (while Firefox sets en_EN). NOTE: Happens when the cookie is not set.
 *		This makes the LanguageSelectors selection appear blank, which is not acceptable.
 *		Since I don't currently have en_US language file I implemented this temporary fix
 */		
		if(locale.equals("en_US")){
			locale = T.system("LOCALE_CODE_ENGLISH_ENGLAND");
		}
		
		return locale;
	}

	public static void setLocale(String locale) {
		String values[] = locale.split("_");
		UI.getCurrent().setLocale(new Locale(values[0], values[1]));
	}
}