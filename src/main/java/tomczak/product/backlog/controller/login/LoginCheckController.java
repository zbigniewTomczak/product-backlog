package tomczak.product.backlog.controller.login;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.user.UserSessionBean;

@RequestScoped @Named
public class LoginCheckController {
	@Inject UserSessionBean userSessionBean;
	@Inject FacesContext fc;
	public void redirectToLoginIfNotLogged() {
		if (!userSessionBean.isLogged()) {
			fc.getApplication().getNavigationHandler(). 
	        handleNavigation(fc, null, 
	        "login"); 
		}
	}
}
