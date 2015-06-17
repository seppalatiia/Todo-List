package fi.my_work.todo_list.components.buttons;

import com.vaadin.ui.Button;

import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.view.LoginView;

public class LogoutButton extends Button implements Button.ClickListener{

	public LogoutButton(){
		
		setCaption(T.get("BUTTON_LOGOUT"));
		setStyleName(T.system("STYLE_BUTTON_LINK_TOP_BAR"));

		addClickListener(this);
	}
	
	public void buttonClick(ClickEvent event) {
//		Logout the user
		getSession().setAttribute(T.system("SESSION_UID"), null);
		getSession().setAttribute(T.system("SESSION_NAME"), null);
		getSession().setAttribute(T.system("SESSION_SELECTED_PROJECT"), null);

//		Navigate the user to the login page
		getUI().getNavigator().navigateTo(LoginView.NAME);
	}
}