package fi.my_work.todo_list.texts;

import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.ui.UI;

public class T {
//	the first part of language file
	private static final String LANGUAGE_FILE = "texts";

//	variables
	private static Locale locale;

	
//	This method returns all the texts that are localized
//	In other words all the text that is shown
//	Excluding few exceptions
	public static String get(String key) {
		locale = getLocale();
		
		try {
			ResourceBundle texts = ResourceBundle.getBundle("fi.my_work.todo_list.texts." + LANGUAGE_FILE, locale);
			
			return texts.getString(key);
		} catch (Exception e) {
			return key;
		}
	}

//	This method returns all system texts
//	e.g. database info, component ids etc.
//	Excluding few exceptions (e.g. theme names)
	public static String system(String key) {
		
		try {
			ResourceBundle texts = ResourceBundle.getBundle("fi.my_work.todo_list.texts." + LANGUAGE_FILE + "_system");
			
			return texts.getString(key);
		} catch (Exception e){
			return key;
		}
	}
	

//	This method gets the language that is to be shown from the UI
	private static Locale getLocale() {
		Locale locale = UI.getCurrent().getLocale();
		
		String[] localesAvailable = system("ALL_LOCALES").split(",");
		
//		If the received locale matches to available languages, the locale is returned
		for (int i = 0; i < localesAvailable.length; i++) {
			if(localesAvailable[i].equals(locale.toString())){
				return locale;
			}
		}
		
//		If the received locale doesn't match to any available locale, default locale is returned
		locale = new Locale("en", "EN");
		
		return locale;
	}
}