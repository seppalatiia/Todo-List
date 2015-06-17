package fi.my_work.todo_list.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.VerticalLayout;

import fi.my_work.todo_list.components.Footer;
import fi.my_work.todo_list.components.TopBar;
import fi.my_work.todo_list.form.LoginForm;
import fi.my_work.todo_list.texts.T;

//  This class creates the login view
public class LoginView extends CustomComponent implements View {

	public static final String NAME = "login";

	CustomLayout content = new CustomLayout(T.system("LAYOUT_VIEW_LAYOUT"));

	TopBar header;
	LoginForm form;
	Footer footer;

	public LoginView() {
		Page.getCurrent().setTitle(T.get("PAGE_TITLE_LOGIN"));

		buildLayout();
	}

	private void buildLayout() {
		validateComponents();
		addComponentsToLayout();
		theming();
	}

	private void validateComponents() {
		header = new TopBar(this);
		form = new LoginForm();
		footer = new Footer();
	}

	private void addComponentsToLayout() {
		content.addComponent(header,	T.system("LAYOUT_VIEW_LAYOUT_LOCATION_TOP_BAR"));
		content.addComponent(form,		T.system("LAYOUT_VIEW_LAYOUT_LOCATION_CONTENT"));
		content.addComponent(footer,	T.system("LAYOUT_VIEW_LAYOUT_LOCATION_FOOTER"));
		
    	setCompositionRoot(content);
	}

	private void theming() {
		content.setSizeFull();
		content.setStyleName(T.system("STYLE_VIEW"));
	}

	public void enter(ViewChangeEvent event) {}

	public String[] getFormValues() {
		return form.getFormValues();
	}

	public void updateComponents(String[] formValues) {
//		Changes the title of the page to match new language
    	Page.getCurrent().setTitle(T.get("PAGE_TITLE_LOGIN"));
    	
    	form.updateComponents(formValues);
    	header.updateComponents();
    	footer.updateComponents();
		
	}
}