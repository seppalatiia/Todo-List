package fi.my_work.todo_list.notification;

import fi.my_work.todo_list.texts.T;

//  This class handles the messages that are shown to the user via notifications
public class Messages {

//	Messages for invalid email
	public static String getMessageInvalidEmail() {
		return T.get("ERROR_MESSAGE__INVALID_EMAIL__MESSAGE");
	}

	public static String[] getMessageWithHeaderInvalidEmail() {
		String[] message = {T.get("ERROR_MESSAGE__LOGIN_FAILED__HEADER"), getMessageInvalidEmail()};
		return message;
	}
	
//	Messages for failed login attempt
	public static String getMessageLoginFailed(){
		return T.get("ERROR_MESSAGE__LOGIN_FAILED__MESSAGE");
	}
	
	public static String[] getMessageWithHeaderLoginFailed(){
		String[] message = {T.get("ERROR_MESSAGE__LOGIN_FAILED__HEADER"), getMessageLoginFailed()};
		
		return message;
	}
	
//	Messages for required field
	public static String getMessageFieldRequired(String fieldName){
		String message = T.get("ERROR_MESSAGE__REQUIRED_FIELD__MESSAGE");
		message = message.replace("FIELDNAME", fieldName);
		message = message.replace(": ", "");
		return message;
	}

	public static String[] getMessageWithHeaderFieldRequired(String fieldName) {
		String[] message = {T.get("ERROR_MESSAGE__REQUIRED_FIELD__HEADER"), getMessageFieldRequired(fieldName)};
		
		return message;
	}
	
//	Messages for unequal passwords
	public static String getMessagePasswordMissmatch() {
		return T.get("ERROR_MESSAGE__PASSWORD_MISMATCH__MESSAGE");
	}
	
	public static String[] getMessageWithHeaderPasswordMissmatch() {
		String[] message = {T.get("ERROR_MESSAGE__PASSWORD_MISMATCH__HEADER"), getMessagePasswordMissmatch()};
		return message;
	}

//	Messages for too short password
	public static String getMessagePasswordTooShort() {
		return T.get("ERROR_MESSAGE__PASSWORD_TOO_SHORT__MESSAGE");
	}
	
	public static String[] getMessageWithHeaderPasswordTooShort() {
		String[] message = {T.get("ERROR_MESSAGE__PASSWORD_TOO_SHORT__HEADER"), getMessagePasswordTooShort()};
		return message;
	}

//	Messages for username in use
	public static String getMessageUsernameInUse() {
		return T.get("ERROR_MESSAGE__USERNAME_ALREADY_IN_USE__MESSAGE");
	}
	
	public static String[] getMessageWithHeaderUsernameInUse() {
		String[] message = {T.get("ERROR_MESSAGE__USERNAME_ALREADY_IN_USE__HEADER"), getMessageUsernameInUse()};
		return message;
	}

//	Messages for email in use
	public static String getMessageEmailInUse() {
		return T.get("ERROR_MESSAGE__EMAIL_ALREADY_IN_USE__MESSAGE");
	}
	
	public static String[] getMessageWithHeaderEmailInUse() {
		String[] message = {T.get("ERROR_MESSAGE__EMAIL_ALREADY_IN_USE__HEADER"), getMessageEmailInUse()};
		return message;
	}

//	Messages for invalid date format
	public static String getMessageInvalidDateFormat() {
		return T.get("ERROR_MESSAGE__INVALID_DATE_FORMAT__MESSAGE");
	}
	
	public static String[] getMessageWithHeaderInvalidDateFormat() {
		String[] message = {T.get("ERROR_MESSAGE__INVALID_DATE_FORMAT__HEADER"), getMessageInvalidDateFormat()};
		return message;
	}
	
//	Message for failed database connection
	public static String[] getMessageWithHeaderDatabaseConnectionFailed() {
		String[] message = {T.get("ERROR_MESSAGE__DATABASE_CONNECTION_FAILED__HEADER"), T.get("ERROR_MESSAGE__DATABASE_CONNECTION_FAILED__MESSAGE")};
		return message;
	}

//	Messages for the situation where email is not allowed
	public static String getMessageCannotBeEmail() {
		return T.get("ERROR_MESSAGE__EMAIL_NOT_ALLOWED__MESSAGE");
	}

	public static String[] getMessageWithHeaderCannotBeEmail() {
		String[] message = {T.get("ERROR_MESSAGE__EMAIL_NOT_ALLOWED__HEADER"), getMessageCannotBeEmail()};
		return message;
	}
}