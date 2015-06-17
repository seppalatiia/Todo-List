package fi.my_work.todo_list.components;

import com.vaadin.ui.ComboBox;

import fi.my_work.todo_list.cookies.CookieManagment;
import fi.my_work.todo_list.locale.Localization;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.view.LoginView;
import fi.my_work.todo_list.view.MainView;
import fi.my_work.todo_list.view.RegisterView;

public class LanguageSelector extends ComboBox{

	private LoginView loginView = null;
	private RegisterView registerView = null;
	private MainView mainView = null;

	/*
	 * Constructors
	 */
	public LanguageSelector(LoginView loginView) {
		this.loginView = loginView;
		
		createCombobox();
	}

	public LanguageSelector(RegisterView registerView) {
		this.registerView = registerView;
		
		createCombobox();
	}

	public LanguageSelector(MainView mainView) {
		this.mainView  = mainView;
		
		createCombobox();
	}

	/*-------------------------------------------------------------*/
	
	private void createCombobox(){
		build();
		select();
		listener();
	}

	private void build() {
//		Add the available languages to the ComboBox
		addItem(T.system("LOCALE_CODE_ENGLISH_ENGLAND"));
		setItemCaption(T.system("LOCALE_CODE_ENGLISH_ENGLAND"), T.system("LOCALE_NAME_ENGLISH_ENGLAND"));
		
		addItem(T.system("LOCALE_CODE_FINNISH_FINLAND"));
		setItemCaption(T.system("LOCALE_CODE_FINNISH_FINLAND"), T.system("LOCALE_NAME_FINNISH_FINLAND"));
		
//		Set the additional settings to the ComboBox
		setNullSelectionAllowed(false);
		setTextInputAllowed(false);
	}

//	Set the selection to match the current language
	private void select() {
		String locale = Localization.getLocale();
		
		select(locale);
	}

//	Add a listener to listen the selections user makes
	private void listener() {
		addValueChangeListener(new ValueChangeListener() {
			
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {

//				Change the language cookie
				CookieManagment.saveCookie(T.system("COOKIE_NAME_LOCALE"), getValue().toString());
				
//				Set the new language in the locale
				Localization.setLocale(getValue().toString());
				
//				When the locale has been changed, update the page
//				The update happens by removing all items from page, re-validating them and finally adding them again
//				In login and register pages, get the values in form, update the page and then re-enter values automatically
				if(loginView != null){
					String[] values = loginView.getFormValues();
					loginView.updateComponents(values);
				}
				else if(registerView != null){
					String[] values = registerView.getFormValues();
					registerView.updateComponents(values);
				}
				else if(mainView != null){
					mainView.updateComponents();
				}
			}
		});
	}
}