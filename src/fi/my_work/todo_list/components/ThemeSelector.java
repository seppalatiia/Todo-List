package fi.my_work.todo_list.components;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;

import fi.my_work.todo_list.cookies.CookieManagment;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.theming.Theming;

public class ThemeSelector extends ComboBox{
	
	public ThemeSelector() {
		createCombobox();
	}

	private void createCombobox() {
		build();
		select();
		listener();
	}

	private void build() {
//		Add the available themes to the ComboBox
		addItem(T.system("THEME_PLAIN_GREY"));
		setItemCaption(T.system("THEME_PLAIN_GREY"), T.system("THEME_PLAIN_GREY_NAME"));
		
		addItem(T.system("THEME_BLACK_LIME"));
		setItemCaption(T.system("THEME_BLACK_LIME"), T.system("THEME_BLACK_LIME_NAME"));
		
		addItem(T.system("THEME_CANDY_RED"));
		setItemCaption(T.system("THEME_CANDY_RED"), T.system("THEME_CANDY_RED_NAME"));

//		Set the additional settings to the ComboBox
		setNullSelectionAllowed(false);
		setTextInputAllowed(false);
	}

//	Set the selection to match the current theme
	private void select() {
		String theme = Theming.getTheme();
		
		select(theme);
	}

//	Add a listener to listen the selections makes
	private void listener() {
		addValueChangeListener(new ValueChangeListener() {
			
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				
//				Change the theme cookie
				CookieManagment.saveCookie(T.system("COOKIE_NAME_THEME"), getValue().toString());
				
//				Set the new theme to the UI
				UI.getCurrent().setStyleName(getValue().toString());
				UI.getCurrent().setTheme(getValue().toString());
				
			}
		});
	}
}