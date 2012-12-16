package tomczak.product.backlog.helper.user;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.model.User;
import tomczak.product.backlog.model.UserData;
import tomczak.product.backlog.model.UserDataName;
import tomczak.product.backlog.qualifiers.CurrentUser;

@RequestScoped
public class CurrentUserDataBean {

	@Inject UserDataBean userDataBean;

	private String firstName;
	private String lastName;
	
	@Produces
	@Named
	public String getCurrentUserFirstName(@CurrentUser Long id) {
		if (firstName == null) {
			firstName = userDataBean.getStringUserData(id, UserDataName.FIRST_NAME_ID);
			if (firstName == null) {
				firstName = "";
			}
		}
		
		return firstName;
	}
	
	@Produces
	@Named
	public String getCurrentUserLastName(@CurrentUser Long id) {
		if (lastName == null) {
			lastName = userDataBean.getStringUserData(id, UserDataName.LAST_NAME_ID);
			if (lastName == null) {
				lastName = "";
			}
		}
		
		return lastName;
	}
	
}
