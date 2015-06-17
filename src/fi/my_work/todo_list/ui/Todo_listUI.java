package fi.my_work.todo_list.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import fi.my_work.todo_list.locale.Localization;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.theming.Theming;
import fi.my_work.todo_list.view.LoginView;
import fi.my_work.todo_list.view.MainView;
import fi.my_work.todo_list.view.RegisterView;

@SuppressWarnings("serial")
@Theme("todo_list")
public class Todo_listUI extends UI implements ViewChangeListener{

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Todo_listUI.class, widgetset = "fi.my_work.todo_list.ui.widgetset.Todo_listWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		setTheme(Theming.getTheme());
		setStyleName(Theming.getTheme());
		
//		Create the Navigator
		new Navigator(this, this);

//		Introduce pages to the navigator
		getNavigator().addView(MainView.NAME, MainView.class);
		getNavigator().addView(LoginView.NAME, LoginView.class);
		getNavigator().addView(RegisterView.NAME, RegisterView.class);
		
		getNavigator().addViewChangeListener(this);
		
//		Set locale 
		Localization.setLocale();
	}
	
	public boolean beforeViewChange(ViewChangeEvent event) {
		boolean isLoggedIn = getSession().getAttribute(T.system("SESSION_UID")) != null;
		boolean isLoginView = LoginView.class.isInstance(event.getNewView());
		
		boolean isRegisterView = event.getNewView() instanceof RegisterView;
		
//		Prevent the user from entering main page if not logged in
		if (!isLoggedIn && !isLoginView) {
//			Allow entrance if register page
			if(isRegisterView){
				return true;
			}
//			Otherwise direct to login page
        	else{
            	getNavigator().navigateTo(LoginView.NAME);
        		return false;
        	}
		}
//		Prevent the user from entering login page if logged in
		else if (isLoggedIn && isLoginView) {
			return false;
		}
//		Prevent the user from entering register page if logged in
		else if (isLoggedIn && isRegisterView) {
			return false;
		}
		
		return true;
	}
	
	public void afterViewChange(ViewChangeEvent event) {}

}