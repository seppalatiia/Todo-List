package fi.my_work.todo_list.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import fi.my_work.todo_list.texts.T;

public class Footer extends HorizontalLayout{

	public Footer(){
		buildFooter();
	}

	private void buildFooter() {
		
//		Creating the elements
		Label lastEditDate = new Label("2015-06-15");
		Link github = new Link(T.get("LINK_TITLE_GITHUB"), new ExternalResource("https://github.com/seppalatiia/Todo-List"));
		Link homepage = new Link(T.get("LINK_TITLE_HOME_PAGE"), new ExternalResource("https://kotikoodarinportfolio.wordpress.com/"));
		
		// Open the URL in a new tab
		github.setTargetName("_blank");
		homepage.setTargetName("_blank");
		
//		Adding items to the layout
		this.addComponent(github);
		this.addComponent(lastEditDate);
		this.addComponent(homepage);

		this.setStyleName(T.system("STYLE_VIEW_FOOTER"));
		lastEditDate.setStyleName(T.system("STYLE_VIEW_FOOTER"));
		
	}

	public void updateComponents() {
		this.removeAllComponents();
		buildFooter();
	}
}