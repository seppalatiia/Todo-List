package fi.my_work.todo_list.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import fi.my_work.todo_list.texts.T;

//  This class is used to show confirmation dialog when user attempts to delete a project or a task
public class MessageWindow extends Window implements Button.ClickListener{

//	This variable tells if an action should be executed when dialog closes
	boolean confirm = false;
	
//	Creating Components
	Button ok = new Button(T.get("BUTTON_OK"));
	Button cancel = new Button(T.get("BUTTON_CANCEL"));
	Label messageLabel;
	
	public MessageWindow(String header, String message){

//		Creating Layouts
		VerticalLayout content = new VerticalLayout();
		HorizontalLayout buttonContainer = new HorizontalLayout();
		
//		Validating the message Label 
		messageLabel = new Label(message);
		messageLabel.setId(T.system("ID_LABEL_CONFIRM_DELETE_MESSAGE"));
		
//		Setting ids to the Buttons
		ok.setId(T.system("ID_BUTTON_CONFIRM_DELETE"));
		cancel.setId(T.system("ID_BUTTON_CANCEL"));
		
//		Adding Listeners to the Buttons
		ok.addClickListener(this);
		cancel.addClickListener(this);

//		Adding components to the layout
		buttonContainer.addComponent(ok);
		buttonContainer.addComponent(cancel);
		
		content.addComponent(messageLabel);
		content.addComponent(buttonContainer);
		
//		Adding header and content to the dialog
		this.setCaption(header);
		this.setContent(content);
		
//		Setting additional info
		this.center();
		this.setModal(true);
		
//		Setting the window visible
		UI.getCurrent().addWindow(this);
	}

//	Functions on ClickListener
	public void buttonClick(ClickEvent event) {
		if(event.getSource().equals(ok)){
			confirm = true;
		}
		
//		The dialog will always close no matter which button is clicked
		this.close();
	}
	
//	When this method is summoned, it tells if the action should or should not be executed
	public boolean isConfirmed(){
		return confirm;
	}
}