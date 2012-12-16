package tomczak.product.backlog.helper.user;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.model.User;
import tomczak.product.backlog.qualifiers.CurrentUser;

@RequestScoped
public class CurrentUserBean {

	@Inject EntityHelper eh;
	private User user;

	@Produces
	@CurrentUser
	@Named
	public User getCurrentUser(@CurrentUser Long id) {
		if (id != null) {
			if(user == null) {
				user = eh.findById(User.class, id);
			} 
			return user;
		}
		
		return null;
	}
	
}
