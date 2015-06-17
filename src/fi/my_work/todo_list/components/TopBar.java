package fi.my_work.todo_list.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import fi.my_work.todo_list.components.LanguageSelector;
import fi.my_work.todo_list.components.ThemeSelector;
import fi.my_work.todo_list.components.buttons.LogoutButton;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.view.LoginView;
import fi.my_work.todo_list.view.MainView;
import fi.my_work.todo_list.view.RegisterView;

public class TopBar extends HorizontalLayout{

	private LoginView loginView = null;
	private RegisterView registerView = null;
	private MainView mainView = null;

	/*
	 * Constructors
//	 * Because LanguageSelector needs the current View user is on, Views are passed to the constructor
	 */
	
	public TopBar(LoginView loginView){
		this.loginView = loginView;
		
		validatedHeader();
	}
	public TopBar(RegisterView registerView){
		this.registerView = registerView;
		
		validatedHeader();
	}
	public TopBar(MainView mainView){
		this.mainView = mainView;
		
		validatedHeader();
	}

	/*-------------------------------------------------------------*/
	
//	Depending the page user is on, the header is slightly different
	private void validatedHeader() {
		if(loginView != null){
			Label greeting = new Label(T.get("LABEL_TOP_BAR_GREETING") + T.get("LABEL_GUEST_USER"));
			LanguageSelector languageSelector = new LanguageSelector(loginView);
			ThemeSelector themeSelector = new ThemeSelector();
			buildHeader(greeting ,null, languageSelector, themeSelector);
		}
		else if(registerView != null){
			Label greeting = new Label(T.get("LABEL_TOP_BAR_GREETING") + T.get("LABEL_GUEST_USER"));
			LanguageSelector languageSelector = new LanguageSelector(registerView);
			ThemeSelector themeSelector = new ThemeSelector();
			buildHeader(greeting ,null, languageSelector, themeSelector);
		}
		else if(mainView != null){
			String username = UI.getCurrent().getSession().getAttribute(T.system("SESSION_NAME")).toString();
			Label greeting = new Label(T.get("LABEL_TOP_BAR_GREETING") + username);
			LanguageSelector languageSelector = new LanguageSelector(mainView);
			ThemeSelector themeSelector = new ThemeSelector();
			LogoutButton logout = new LogoutButton();
			buildHeader(greeting ,logout, languageSelector, themeSelector);
		}
		setStyleName(T.system("STYLE_VIEW_TOP_BAR"));
	}
	
//	This method adds the items to the header
	private void buildHeader(Label greeting, LogoutButton logout, LanguageSelector languageSelector, ThemeSelector themeSelector) {

//		Add a style name for the greeting label
		greeting.setStyleName(T.system("STYLE_LABEL_GREETING"));
		
//		Add components to the layout
		addComponent(greeting);
		if(logout != null){
			addComponent(logout);
		}
		addComponent(languageSelector);
		addComponent(themeSelector);
		
		setIds(greeting, logout, languageSelector, themeSelector);
	}
	
//	This method applies the changes that changing language requires
	public void updateComponents() {
		removeAllComponents();
		validatedHeader();
	}

//	Set id's for the components
	private void setIds(Label greeting, LogoutButton logout, LanguageSelector languageSelector, ThemeSelector themeSelector){
		
		greeting.setId(T.system("ID_LABEL_GREETING"));
		if(logout != null){
			logout.setId(T.system("ID_BUTTON_LOGOUT"));
		}
		languageSelector.setId(T.system("ID_COMBOBOX_LANGUAGE_SELECTOR"));
		themeSelector.setId(T.system("ID_COMBOBOX_THEME_SELECTOR"));
		
	}
}