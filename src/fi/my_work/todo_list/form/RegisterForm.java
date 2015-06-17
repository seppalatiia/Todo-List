package fi.my_work.todo_list.form;

import java.util.ArrayList;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import fi.my_work.todo_list.db.DBInsert;
import fi.my_work.todo_list.db.DBSelect;
import fi.my_work.todo_list.db.dummy_data.DummyData;
import fi.my_work.todo_list.notification.Messages;
import fi.my_work.todo_list.notification.Notifications;
import fi.my_work.todo_list.texts.T;
import fi.my_work.todo_list.view.LoginView;
import fi.my_work.todo_list.view.MainView;

public class RegisterForm extends CustomLayout implements Button.ClickListener, ValueChangeListener{
	
	private Label header;
	
    private TextField username;
    private CheckBox emailAsUsername;
    private TextField email;
    private PasswordField password;
    private PasswordField confirmPassword;
    
    private Button registerButton;
    private Link loginLink;

	private String helperText;
	
	public RegisterForm(){
		super(T.system("LAYOUT_FORM_REGISTER"));
		
		validateComponents();
		addComponentsToLayout();
		setIds();
		theming();
	}

	public void buttonClick(ClickEvent event) {
		String user = username.getValue();
		boolean useEmailAsUser = emailAsUsername.getValue();
		String emailStr = email.getValue();
		String pass = password.getValue();
		String confirmPass = confirmPassword.getValue();
		
		ArrayList<String[]> errors = new ArrayList<String[]>();

		EmailValidator mustNotBeEmail = new EmailValidator(Messages.getMessageCannotBeEmail());
		
//		TODO vaihda usernamen järjestystä ???
		
		/*
		 * USERNAME
		 */
		
//		If username is going to be email, it's not required to check validity of username
		if (useEmailAsUser) {
			username.setComponentError(null);
		}
//		If an email is written in username field an errormessage is shown.
//		When user wishes to use an email as username, user must chack that option instead of writing it in Username-field.
//		This is because the email that is used as username must be same as the one in Email-field and user might insert different email as username.
		else if(mustNotBeEmail.isValid(username)){
			username.setComponentError(new UserError(Messages.getMessageCannotBeEmail()));
			errors.add(Messages.getMessageWithHeaderCannotBeEmail());
		}
//		Check that the username is given
		else if(!username.isValid()){
			username.setComponentError(new UserError(Messages.getMessageFieldRequired(username.getCaption())));
			errors.add(Messages.getMessageWithHeaderFieldRequired(username.getCaption()));
		}
//		Check if the username is already in use
		else if(DBSelect.isUserNameInUse(user)){
			username.setComponentError(new UserError(Messages.getMessageUsernameInUse()));
			errors.add(Messages.getMessageWithHeaderUsernameInUse());
		}
		else{
			username.setComponentError(null);
		}
		
		/*
		 * EMAIL
		 */
		
//		Check that the email is given
		if(emailStr.equals("")){
			email.setComponentError(new UserError(Messages.getMessageFieldRequired(email.getCaption())));
			errors.add(Messages.getMessageWithHeaderFieldRequired(email.getCaption()));
		}
//		Verify that the email is valid
		else if(!email.isValid()){
			errors.add(Messages.getMessageWithHeaderInvalidEmail());
			email.setComponentError(new UserError(Messages.getMessageInvalidEmail()));
		}
//		Check if the email is already in use
		else if(DBSelect.isEmailInUse(emailStr)){
			email.setComponentError(new UserError(Messages.getMessageEmailInUse()));
			errors.add(Messages.getMessageWithHeaderEmailInUse());
		}
		else{
			email.setComponentError(null);
		}
		
		/*
		 * PASSWORD
		 */
		
//		Check that the password is given
		if(!password.isValid()){
			password.setComponentError(new UserError(Messages.getMessageFieldRequired(password.getCaption())));
			errors.add(Messages.getMessageWithHeaderFieldRequired(password.getCaption()));
		}
//		Verify that the password is at least eight characters
		else if(pass.length() < 8){
			password.setComponentError(new UserError(Messages.getMessagePasswordTooShort()));
			errors.add(Messages.getMessageWithHeaderPasswordTooShort());
		}
		else{
			password.setComponentError(null);
		}
		
		/*
		 * CONFIRM PASSWORD
		 */
		
//		Check that the password confirmation is given
		if(!confirmPassword.isValid()){
			confirmPassword.setComponentError(new UserError(Messages.getMessageFieldRequired(confirmPassword.getCaption())));
			errors.add(Messages.getMessageWithHeaderFieldRequired(confirmPassword.getCaption()));
		}
//		Verify that the password confirmation matches password
		else if(!pass.equals(confirmPass)){
			confirmPassword.setComponentError(new UserError(Messages.getMessagePasswordMissmatch()));
			errors.add(Messages.getMessageWithHeaderPasswordMissmatch());
		}
		else{
			confirmPassword.setComponentError(null);
		}
		
		/*
		 * NOTIFICATION
		 */
		
		int uid = -1;
//		If the errors ArrayList size is not 0 Notification is shown at this point so there won't be unnecessary sql queries
		if(errors.size() != 0){
			Notifications.throwError(errors);
			return;
		}

		/*
		 * DUMMY DATA
		 */
		
//		If the useDummyData is true, nothing will be done
		if (DummyData.useDummyData()) {
			return;
		}

		/*
		 * REGISTER
		 */
		
		if (useEmailAsUser) {
			uid = DBInsert.user(emailStr, emailStr, pass);
		}
		else{
			uid = DBInsert.user(user, emailStr, pass);
		}
		
//		The user will be automatically logged in if creation of account is successful
		getSession().setAttribute(T.system("SESSION_UID"), uid);
		getSession().setAttribute(T.system("SESSION_NAME"), user);
		getSession().setAttribute(T.system("SESSION_SELECTED_PROJECT"), "-1");
		
		getUI().getNavigator().navigateTo(MainView.NAME);
		
	}

	private void validateComponents() {

//		Header
		header = new Label(T.get("LABEL_REGISTER_FORM_HEADER"), ContentMode.HTML);
		
//		Fields
		username = new TextField(T.get("TEXTFIELD_USERNAME"));
		username.setRequired(true);
		
		emailAsUsername = new CheckBox(T.get("CHECKBOX_EMAIL_AS_USERNAME"));
		emailAsUsername.addValueChangeListener(this);

		email = new TextField(T.get("TEXTFIELD_EMAIL"));
		email.setRequired(true);
		
		password = new PasswordField(T.get("PASSWORDFIELD_PASSWORD"));
		password.setRequired(true);
		
		confirmPassword = new PasswordField(T.get("PASSWORDFIELD_CONFIRM_PASSWORD"));
		confirmPassword.setRequired(true);
		
//		Buttons
		registerButton = new Button(T.get("BUTTON_REGISTER"));
		registerButton.addClickListener(this);
		
		loginLink = new Link(T.get("BUTTON_LOGIN"), new ExternalResource("#!" + LoginView.NAME));
		
//		helper text for username field
		helperText = T.get("TEXTFIELD_EMAIL_HELPER");
		email.setInputPrompt(helperText);
		
//		Validator
		EmailValidator emailValidator = new EmailValidator(Messages.getMessageInvalidEmail());
		email.addValidator(emailValidator);
	}

	private void addComponentsToLayout() {
    	addComponent(header,			T.system("LAYOUT_FORM_REGISTER_LOCATION_HEADER"));
    	addComponent(username,			T.system("LAYOUT_FORM_REGISTER_LOCATION_USERNAME"));
    	addComponent(emailAsUsername,	T.system("LAYOUT_FORM_REGISTER_LOCATION_EMAIL_AS_USERNAME"));
    	addComponent(email,				T.system("LAYOUT_FORM_REGISTER_LOCATION_EMAIL"));
    	addComponent(password,			T.system("LAYOUT_FORM_REGISTER_LOCATION_PASSWORD"));
    	addComponent(confirmPassword,	T.system("LAYOUT_FORM_REGISTER_LOCATION_CONFIRM_PASSWORD"));
    	addComponent(registerButton,	T.system("LAYOUT_FORM_REGISTER_LOCATION_REGISTER"));
    	addComponent(loginLink,			T.system("LAYOUT_FORM_REGISTER_LOCATION_LOGIN"));
	}

	private void setIds() {
		header.setId(T.system("ID_LABEL_HEADER"));
		username.setId(T.system("ID_TEXTFIELD_USERNAME"));
		email.setId(T.system("ID_TEXTFIELD_EMAIL"));
        password.setId(T.system("ID_TEXTFIELD_PASSWORD"));
        confirmPassword.setId(T.system("ID_TEXTFIELD_CONFIRM_PASSWORD"));
        registerButton.setId(T.system("ID_BUTTON_SUBMIT"));
        loginLink.setId(T.system("ID_BUTTON_LOGIN"));
	}

	private void theming() {
		setStyleName(T.system("STYLE_VIEW_CONTENT_CONTAINER"));
		
    	setWidth(T.system("VIEW_REGISTER_WIDTH_NORMAL"));
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
		if (formValues[1].equals("true")) {
			emailAsUsername.setValue(true);
		}
		if (formValues[1].length() != 0) {
			email.setValue(formValues[2]);
		}
		if (formValues[2].length() != 0) {
			password.setValue(formValues[3]);
		}
		if (formValues[3].length() != 0) {
			confirmPassword.setValue(formValues[4]);
		}
	}

	public String[] getFormValues() {
		String[] values = {username.getValue(),emailAsUsername.getValue().toString(), email.getValue(), password.getValue(), confirmPassword.getValue()};
		return values;
	}

//	When the emailAsUsername is clicked, the username-field is enabled or disabled
	public void valueChange(ValueChangeEvent event) {
		if (emailAsUsername.getValue()) {
			username.setEnabled(false);
		}
		else{
			username.setEnabled(true);
		}
	}
}