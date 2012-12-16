package tomczak.product.backlog.helper.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import tomczak.product.backlog.qualifiers.CurrentUser;

@SessionScoped @Named
public class UserSessionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long currentUserId = null;

	@Produces
	@CurrentUser
	public Long currentUserId() {
		return currentUserId;
	}
	
	public String logout() {
		currentUserId = null;
		return "login";
	}

	public void setCurrentUserId(Long userId) {
		currentUserId = userId;
	}

	public boolean isLogged() {
		return currentUserId != null;
	}
	
	
}
