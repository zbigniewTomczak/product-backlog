package tomczak.product.backlog.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@SessionScoped @Named
public class UserSessionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long currentUserId = null;

	@Produces
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
	
	
}
