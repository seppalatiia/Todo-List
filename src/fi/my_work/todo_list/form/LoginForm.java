package fi.my_work.todo_list.form;

import java.util.ArrayList;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;

import fi.my_work.todo_list.db.DBSelect;
import fi.my_work.todo_list.notification.Notifications;
import fi.my_work.todo_list.notification.Messages;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.view.MainView;
import fi.my_work.todo_list.view.RegisterView;

public class LoginForm extends CustomLayout implements Button.ClickListener{

	private Label header;
	
    private TextField username;
    private PasswordField password;
    
    private Button loginButton;
    private Link registerLink;
	
	public LoginForm(){
		super(T.system("LAYOUT_FORM_LOGIN"));
		
		validateComponents();
		addComponentsToLayout();
		setIds();
		theming();
	}

	public void buttonClick(ClickEvent event) {
		String user = username.getValue();
		String pass = password.getValue();
		
		ArrayList<String[]> errors = new ArrayList<String[]>();
		
//		Checking the validity of the username
		if(!username.isValid()){
			username.setComponentError(new UserError(Messages.getMessageFieldRequired(username.getCaption())));
			errors.add(Messages.getMessageWithHeaderFieldRequired(username.getCaption()));
		}
		else{
			username.setComponentError(null);
		}
		
//		Checking the validity of the password
		if(!password.isValid()){
			password.setComponentError(new UserError(Messages.getMessageFieldRequired(password.getCaption())));
			errors.add(Messages.getMessageWithHeaderFieldRequired(password.getCaption()));
		}
		else{
			password.setComponentError(null);
		}
		
//		If the errors ArrayList size is not 0 Notification is shown at this point so there won't be unnecessary sql queries
		if(errors.size() != 0){
			Notifications.throwError(errors);
			return;
		}
		
//		Retrieving the user id
		int uid = DBSelect.userId(user, pass);
		
//		If the user isn't on database or the password doesn't match login is prevented
		if(uid == -1){
			username.setComponentError(new UserError(Messages.getMessageLoginFailed()));
			password.setComponentError(new UserError(Messages.getMessageLoginFailed()));
			
			password.setValue("");
			Notifications.throwSingleError(Messages.getMessageWithHeaderLoginFailed());
		}
		else if(uid == -2){
//			If -2 is returned it means that some error happened during the attempt to retrieve information from database.
//			It also means that a Notification has been shown to the user already so it is not necessary to show another one.
		}
		else{
			getSession().setAttribute(T.system("SESSION_UID"), uid);
			getSession().setAttribute(T.system("SESSION_NAME"), user);
			getSession().setAttribute(T.system("SESSION_SELECTED_PROJECT"), "-1");
			
			getUI().getNavigator().navigateTo(MainView.NAME);
		}
	}
	
	private void validateComponents() {
//		Header
		header = new Label(T.get("LABEL_LOGIN_FORM_HEADER"), ContentMode.HTML);
		
//		Fields
		username = new TextField(T.get("TEXTFIELD_USERNAME"));
		username.setRequired(true);
		
		password = new PasswordField(T.get("PASSWORDFIELD_PASSWORD"));
		password.setRequired(true);
		
//		Buttons
		loginButton = new Button(T.get("BUTTON_LOGIN"));
		loginButton.addClickListener(this);
		
		registerLink = new Link(T.get("BUTTON_REGISTER"), new ExternalResource("#!" + RegisterView.NAME));
	}

	private void addComponentsToLayout() {
    	addComponent(header, T.system("LAYOUT_FORM_LOGIN_LOCATION_HEADER"));
    	addComponent(username, T.system("LAYOUT_FORM_LOGIN_LOCATION_USERNAME"));
    	addComponent(password, T.system("LAYOUT_FORM_LOGIN_LOCATION_PASSWORD"));
    	addComponent(loginButton, T.system("LAYOUT_FORM_LOGIN_LOCATION_LOGIN"));
    	addComponent(registerLink, T.system("LAYOUT_FORM_LOGIN_LOCATION_REGISTER"));
	}

	private void setIds() {
		header.setId(T.system("ID_LABEL_HEADER"));
		username.setId(T.system("ID_TEXTFIELD_USERNAME"));
        password.setId(T.system("ID_TEXTFIELD_PASSWORD"));
		loginButton.setId(T.system("ID_BUTTON_SUBMIT"));
		registerLink.setId(T.system("ID_BUTTON_REGISTER"));
	}

	private void theming() {
		setStyleName(T.system("STYLE_VIEW_CONTENT_CONTAINER"));
		
    	setWidth(T.system("VIEW_LOGIN_WIDTH_NORMAL"));
	}

	
//	Following three methods are used to keep values on the fields when language is changed
	public void updateComponents(String[] formValues) {
		removeAllComponents();
		
		validateComponents();
		addComponentsToLayout();
		setIds();
		theming();
		setFieldValues(formValues);
	}

	private void setFieldValues(String[] formValues) {
		if (formValues[0].length() != 0) {
			username.setValue(formValues[0]);
		}
		if (formValues[1].length() != 0) {
			password.setValue(formValues[1]);
		}
	}

	public String[] getFormValues() {
		String[] values = {username.getValue(), password.getValue()};
		return values;
	}
}